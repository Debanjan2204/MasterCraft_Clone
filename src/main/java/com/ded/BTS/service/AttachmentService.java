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

@Service
public class AttachmentService {
	
	@Value("${attachments.upload.dir}")
	private String uploadDir;
	
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
	private AttachmentRepo attachmentRepo;
	public AttachmentService(AttachmentRepo attachmentRepo) {
		super();
		this.attachmentRepo = attachmentRepo;
	}



	public Attachment uploadAttachment(String entityType, Long entityId, MultipartFile file){
		
		try {
		validateFile(file);
		String originalName= file.getOriginalFilename();
		String extension=  	originalName.substring(originalName.lastIndexOf("."));	
		String storedName=UUID.randomUUID()+extension;
		Path path = Paths.get(uploadDir).resolve(storedName); 	
		Files.copy(file.getInputStream(),path);
		
		Attachment attachment = new Attachment();
		attachment.setEntityType(entityType);
		attachment.setEntityId(entityId);
		attachment.setOriginalName(originalName);
		attachment.setStoredName(storedName);
		attachment.setContentType(file.getContentType());
		attachment.setSize(file.getSize());
		attachment.setFilePath(path.toString());	
		return attachmentRepo.save(attachment);
		}catch (IOException e) {
			throw new ProcessingException("Exception occured while uploading attachments of "+entityType+" Id "+entityId,e);
		}
	}
	
	
	public List<Attachment> getAttachmentsforEntity(String entityType, Long entityId){
		return attachmentRepo.findByEntityTypeAndEntityId(entityType, entityId);
	}


	public Attachment findAttachmentById(Long id) {
		return attachmentRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Attachment with id "
				+id +" not found"));
	}
	
	@Transactional
	public String deleteAttachmentsbyticket(Long ticketId) {
		List<Attachment> attachments= attachmentRepo.findByEntityTypeAndEntityId("TICKET", ticketId);
		attachments.forEach(attachment-> {
			attachment.setRecEndDate(Instant.now());
			try {
			MoveToArchive(attachment.getFilePath());
			}catch (IOException e) {
				throw new ProcessingException("Exception occured while uploading attachments of Ticket Id "+ticketId,e);
			}
			}
			);
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
	private void MoveToArchive( String filePath) throws IOException {
		 Path source= Paths.get(filePath);
		 Path archivedDir = Paths.get(uploadDir,"archived");
		 Files.createDirectories(archivedDir);
		 Path target = archivedDir.resolve(source.getFileName());
		 Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		 
	}
	
}
