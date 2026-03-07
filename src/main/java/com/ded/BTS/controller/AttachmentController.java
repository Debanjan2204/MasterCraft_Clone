package com.ded.BTS.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ded.BTS.model.Attachment;
import com.ded.BTS.service.AttachmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = "oauth2")
public class AttachmentController {

	private AttachmentService attachmentService;
	
	
	
	public AttachmentController(AttachmentService attachmentService) {
		super();
		this.attachmentService = attachmentService;
	}



	@PostMapping(
		    value = "/tickets/{ticketId}/attachments",
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Tag(name = "Ticket - Attachment Upload Operations")
	@Operation(summary = "upload ticket attachment")
	public ResponseEntity<Attachment> upload(@PathVariable Long ticketId,@RequestParam("file") MultipartFile file) {

	    return ResponseEntity.ok().body(attachmentService.uploadAttachment("TICKET", ticketId, file));
	}

	
	@GetMapping("/tickets/{ticketId}/attachments")
	@Tag(name = "Ticket - Attachment  Read Operations")
	@Operation(summary = "get all ticket attachments")
	public ResponseEntity<List<Attachment>> getAttachments(@PathVariable Long ticketId){
		
		return ResponseEntity.ok().body( attachmentService.getAttachmentsforEntity("TICKET", ticketId));
	}
	
	@GetMapping("download-attachment/{id}")
	@Tag(name = "Ticket - Attachment Download Operations")
	@Operation(summary = "download ticket attachment")
	public ResponseEntity<byte[]> download(@PathVariable Long id) {
	    Attachment attachment = attachmentService.findAttachmentById(id);
	    byte[] data = attachmentService.downloadAttachment(id);
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(attachment.getContentType()))
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" + attachment.getOriginalName() + "\"")
	            .body(data);
	}
	
	
	@DeleteMapping("/tickets/{ticketId}/attachments")
	@Tag(name = "Ticket - Attachment delete Operations")
	@Operation(summary = "delete ticket attachments")
	public ResponseEntity<String> deleteAttachments( @PathVariable("ticketId") Long ticketId){
		return ResponseEntity.ok(attachmentService.deleteAttachmentsbyticket(ticketId));
	}
	
}
