package com.ded.BTS.controller;

import java.util.List;

import org.hibernate.boot.model.internal.Nullability;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.DTO.request.CreateTicketRequest;
import com.ded.BTS.DTO.request.UpdateTicketRequest;
import com.ded.BTS.DTO.response.CreateTicketResponse;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.enums.TicketPriority;
import com.ded.BTS.enums.TicketStatus;
import com.ded.BTS.service.TicketService;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.*;

@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = "oauth2")
//@CrossOrigin()
public class BasicTicketController {
	
	private final TicketService ticketService;
	
	public BasicTicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}

	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Get all tickets")
	@GetMapping("/tickets")
	public ResponseEntity<List<TicketResponse>> findAllTickets( @Nullable @RequestParam("status") TicketStatus ticketStatus,
			@Nullable @RequestParam("priority") TicketPriority ticketPriority,
			@Nullable @RequestParam("title") String title) {
		List<TicketResponse> ticketResponse = null;

		if (ticketStatus == null && ticketPriority == null && title == null) {
			ticketResponse = ticketService.getAllTickets();
		}else {
			ticketResponse = ticketService.getTicketByStatusOrPriorityOrName(ticketStatus, ticketPriority, title);
		}

		return ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
	}

	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Get ticket by ID")
	@GetMapping("/tickets/{id}")
	public ResponseEntity<TicketResponse> findTicket(@PathVariable Long id){
		TicketResponse ticketResponse= ticketService.getTicketById(id);
		
		return  ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
	}
	
//	@Tag(name = "1️⃣ Ticket - Read Operations")
//	@Operation(summary = "Get ticket by status or priority")
//	@GetMapping("/tickets")
//	public ResponseEntity<List<TicketResponse>> findTicket(@RequestParam("status") TicketStatus ticketStatus , @RequestParam("priority") TicketPriority ticketPriority){
//	List<TicketResponse> ticketResponse= ticketService.getTicketByStatusOrPriority(ticketStatus, ticketPriority);
//		
//		return  ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
//	}

	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Create ticket")
	@PostMapping("/tickets")
	public ResponseEntity<CreateTicketResponse> createTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest){
		
		CreateTicketResponse createTicketResponse= ticketService.createTicket(createTicketRequest);
		return  ResponseEntity.status(HttpStatus.CREATED).body(createTicketResponse);
	}
	
	@Tag(name = "2️⃣ Ticket - Write Operations")
	@Operation(summary = "Update ticket")
	@PutMapping("/tickets/{id}")
	public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id , @Valid @RequestBody UpdateTicketRequest updateTicketRequest ){
		TicketResponse ticketResponse = ticketService.updateTicket(id, updateTicketRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketResponse);
	}

	@Tag(name = "3️⃣ Ticket - Dangerous Operations")
	@Operation(summary = "Delete ticket permanently")
	@DeleteMapping("/tickets/{id}")
	public ResponseEntity<Boolean> deleteTicket(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.deleteTicket(id));
	}
	
}
