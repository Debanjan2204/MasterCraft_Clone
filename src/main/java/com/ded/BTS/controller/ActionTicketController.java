package com.ded.BTS.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ded.BTS.AI.service.GeminiService;
import com.ded.BTS.DTO.request.AssignTicketRequest;
import com.ded.BTS.DTO.request.ChangeDueDateRequest;
import com.ded.BTS.DTO.request.ChangePriorityRequest;
import com.ded.BTS.DTO.request.ChangeStatusRequest;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.enums.TicketPriority;
import com.ded.BTS.enums.TicketStatus;
import com.ded.BTS.service.TicketService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tickets")
@SecurityRequirement(name = "oauth2")
public class ActionTicketController {

	private final TicketService ticketService;
	public ActionTicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}

	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Assing a ticket to an user")
	@PutMapping("/{ticketId}/assign")
	public ResponseEntity<TicketResponse> assignTicket(@Valid @RequestBody AssignTicketRequest assignTicketRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.assignTicket(ticketId, assignTicketRequest.userName()));
	}

	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Change ticket Status")
	@PutMapping("/{ticketId}/status")
	public ResponseEntity<TicketResponse> changeTicketStatus(@Valid @RequestBody ChangeStatusRequest changeStatusRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.changeTicketStatus(ticketId, changeStatusRequest.status()));
	}

	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Update ticket Priority")
	@PutMapping("/{ticketId}/priority")
	public ResponseEntity<TicketResponse> changeTicketPriority(@Valid @RequestBody ChangePriorityRequest changePriorityRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.setPriority(ticketId, changePriorityRequest.priority()));
	}

	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Change ticket due date")
	@PutMapping("/{ticketId}/due-date")
	public ResponseEntity<TicketResponse> changeTicketDueDate(
			@Valid @RequestBody ChangeDueDateRequest changeDueDateRequest,
			@PathVariable Long ticketId) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.setDueDate(ticketId, changeDueDateRequest.dueDate()));
	}
	
	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Summarize all comments for a ticket")
	@GetMapping("/summarize-tickets/{ticketId}")
	public ResponseEntity<String> getTicketSummary(@PathVariable Long ticketId) {
		System.out.println(">>>INVOKED TICKET SUMMARY");
		String responseString= ticketService.getSummarizedComments(ticketId);
		System.out.println(">>>returned from TICKET SUMMARY");
		System.out.println(">>>RESPONSE - > "+responseString);
		return ResponseEntity.status(HttpStatus.OK).body(responseString);
	}

	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Summarize all comments for a ticket")
	@GetMapping("/test/{prompt}")
	public ResponseEntity<String> test( @PathVariable String prompt) {
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.test(prompt));
	}

}
