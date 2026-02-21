package com.ded.BTS.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ded.BTS.DTO.request.AssignTicketRequest;
import com.ded.BTS.DTO.request.ChangeDueDateRequest;
import com.ded.BTS.DTO.request.ChangePriorityRequest;
import com.ded.BTS.DTO.request.ChangeStatusRequest;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.service.TicketService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@SecurityRequirement(name = "oauth2")
public class ActionTicketController {

	private final TicketService ticketService;

	public ActionTicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}

	@PostMapping("/{ticketId}/assign")
	public ResponseEntity<TicketResponse> assignTicket(@Valid @RequestBody AssignTicketRequest assignTicketRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.assignTicket(ticketId, assignTicketRequest.userName()));
	}

	@PostMapping("/{ticketId}/status")
	public ResponseEntity<TicketResponse> changeTicketStatus(@Valid @RequestBody ChangeStatusRequest changeStatusRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.changeTicketStatus(ticketId, changeStatusRequest.status()));
	}

	@PostMapping("/{ticketId}/priority")
	public ResponseEntity<TicketResponse> changeTicketPriority(@Valid @RequestBody ChangePriorityRequest changePriorityRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.setPriority(ticketId, changePriorityRequest.priority()));
	}

	@PostMapping("/{ticketId}/due-date")
	public ResponseEntity<TicketResponse> changeTicketDueDate(
			@Valid @RequestBody ChangeDueDateRequest changeDueDateRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.setDueDate(ticketId, changeDueDateRequest.dueDate()));
	}

}
