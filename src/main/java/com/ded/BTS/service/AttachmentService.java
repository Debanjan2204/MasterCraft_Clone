package com.ded.BTS.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ded.BTS.Exceptions.ProcessingException;
import com.ded.BTS.beans.FileTypeDetector;
import com.ded.BTS.model.Attachment;
import com.ded.BTS.repository.AttachmentRepo;
import com.ded.BTS.security.model.CurrentUser;

import jakarta.persistence.EntityNotFoundException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class AttachmentService {

    private static final Set<String> ALLOWED_MIME = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "image/png",
            "image/jpeg",
            "text/plain"
    );

    private final AttachmentRepo attachmentRepo;
    private final S3Client s3Client;

    @Value("${b2.bucket.name}")
    private String bucketName;

    public AttachmentService(AttachmentRepo attachmentRepo, S3Client s3Client) {
        this.attachmentRepo = attachmentRepo;
        this.s3Client = s3Client;
    }

    public Attachment uploadAttachment(String entityType, Long entityId, MultipartFile file) {
        try {
            validateFile(file);
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String storedName = UUID.randomUUID() + extension;
            String b2Key = "uploads/" + storedName; // key in B2

            // Upload to B2
            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(b2Key)
                    .contentType(file.getContentType())
                    .build(),
                RequestBody.fromBytes(file.getBytes())
            );

            Attachment attachment = new Attachment();
            attachment.setEntityType(entityType);
            attachment.setEntityId(entityId);
            attachment.setOriginalName(originalName);
            attachment.setStoredName(storedName);
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            attachment.setFilePath(b2Key); // store B2 key instead of local path

            return attachmentRepo.save(attachment);

        } catch (IOException e) {
            throw new ProcessingException("Exception occurred while uploading attachments of " + entityType + " Id " + entityId, e);
        }
    }

    public byte[] downloadAttachment(Long id) {
        Attachment attachment = findAttachmentById(id);

        ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(
            GetObjectRequest.builder()
                .bucket(bucketName)
                .key(attachment.getFilePath()) // filePath is now B2 key
                .build()
        );

        return response.asByteArray();
    }

    public List<Attachment> getAttachmentsforEntity(String entityType, Long entityId) {
        return attachmentRepo.findByEntityTypeAndEntityId(entityType, entityId);
    }

    public Attachment findAttachmentById(Long id) {
        return attachmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attachment with id " + id + " not found"));
    }

    @Transactional
    public String deleteAttachmentsbyticket(Long ticketId) {
        List<Attachment> attachments = attachmentRepo.findByEntityTypeAndEntityId("TICKET", ticketId);
        attachments.forEach(attachment -> {
            attachment.setRecEndDate(Instant.now());
            String b2Key=attachment.getFilePath();
            try {

                String filename = b2Key.substring(b2Key.lastIndexOf("/") + 1);
                String archivedKey = "archived/" + filename;

                // Copy to archived folder
                s3Client.copyObject(
                    CopyObjectRequest.builder()
                        .sourceBucket(bucketName)
                        .sourceKey(b2Key)
                        .destinationBucket(bucketName)
                        .destinationKey(archivedKey)
                        .build()
                );

                // Delete from original location
             // List all versions of the file
                ListObjectVersionsResponse versions = s3Client.listObjectVersions(
                    ListObjectVersionsRequest.builder()
                        .bucket(bucketName)
                        .prefix(b2Key)
                        .build()
                );

                // Delete all versions
                versions.versions().forEach(version -> {
                    s3Client.deleteObject(
                        DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(version.key())
                            .versionId(version.versionId())
                            .build()
                    );
                });

                // Also delete any hide markers
                versions.deleteMarkers().forEach(marker -> {
                    s3Client.deleteObject(
                        DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(marker.key())
                            .versionId(marker.versionId())
                            .build()
                    );
                });
                        } catch (Exception e) {
                throw new ProcessingException("Exception occurred while archiving attachments of Ticket Id " + ticketId, e);
            }
        });
        return "Success";
    }

    private void validateFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String mimeType = FileTypeDetector.detectMime(file.getInputStream());
        if (!ALLOWED_MIME.contains(mimeType)) {
            throw new IllegalArgumentException("Unsupported file type: " + mimeType);
        }
    }

    private void moveToArchive(String b2Key) {}
}