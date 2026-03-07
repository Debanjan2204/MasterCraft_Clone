package com.ded.BTS.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "*")
@RestController()
@RequestMapping("/api/users/")
public class QueryTicketController {

	private final TicketService ticketService;
	
	public QueryTicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}
	
	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Get all assigned tickets")
	@GetMapping("/{userName}/assigned-tickets")
	public ResponseEntity<List<TicketResponse>> findassignedTickets(@PathVariable String userName){
		return  ResponseEntity.status(HttpStatus.OK).body(ticketService.getTicketsByAssignee(userName));
	}

	@Tag(name = "1️⃣ Ticket - Read Operations")
	@Operation(summary = "Get all reported tickets")
	@GetMapping("/reported-tickets")
	public ResponseEntity<List<TicketResponse>> findreportedTickets(){
		return  ResponseEntity.status(HttpStatus.OK).body(ticketService.getTicketsByReporter());
	}

}
