package com.ded.BTS.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.DTO.request.AddCommentRequest;
import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = "oauth2")
@CrossOrigin(origins = "*")
public class TicketCommentController {

	private final TicketService ticketService;

	public TicketCommentController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}
	
	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Get all comments on a ticket")
	@GetMapping("/tickets/{id}/comments")
	public ResponseEntity<Object> getCommentByTicket(@PathVariable("id") Long ticketId) {
		
	    List<TicketCommentResponse> ticketComments = ticketService.getCommentsByTicket(ticketId);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(ticketComments);
	}
	
	
	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Add ticket comment")
	@PostMapping("/tickets/{id}/comments")
	public ResponseEntity<Object> addComment(@PathVariable("id") Long ticketId,@Valid @RequestBody AddCommentRequest addCommentRequest) {
		
	    TicketCommentResponse ticketCommentResponse = ticketService.addComment(ticketId, addCommentRequest);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(ticketCommentResponse);
	}

}
