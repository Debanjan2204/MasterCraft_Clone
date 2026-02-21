package com.ded.BTS.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.DTO.request.AddCommentRequest;
import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class TicketCommentController {

	private final TicketService ticketService;

	public TicketCommentController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}
	
	
	@GetMapping("/tickets/{id}/comments")
	public ResponseEntity<Object> getCommentByTicket(@PathVariable("id") Long ticketId) {
		
	    List<TicketCommentResponse> ticketComments = ticketService.getCommentsByTicket(ticketId);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(ticketComments);
	}
	
	
	
	@PostMapping("/tickets/{id}/comments")
	public ResponseEntity<Object> addComment(@PathVariable("id") Long ticketId,@Valid @RequestBody AddCommentRequest addCommentRequest) {
		
	    TicketCommentResponse ticketCommentResponse = ticketService.addComment(ticketId, addCommentRequest);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(ticketCommentResponse);
	}

}
