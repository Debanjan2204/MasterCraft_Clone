package com.ded.BTS.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.DTO.request.CreateTicketRequest;
import com.ded.BTS.DTO.request.UpdateTicketRequest;
import com.ded.BTS.DTO.response.CreateTicketResponse;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.service.TicketService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = "oauth2")
public class BasicTicketController {
	
	private final TicketService ticketService;
	
	public BasicTicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}

	@GetMapping("/tickets")
	public ResponseEntity<List<TicketResponse>> findAllTickets(){
		List<TicketResponse> ticketResponse= ticketService.getAllTickets();
		
		return  ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
	}

	@GetMapping("/tickets/{id}")
	public ResponseEntity<TicketResponse> findTicket(@PathVariable Long id){
		TicketResponse ticketResponse= ticketService.getTicketById(id);
		
		return  ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
	}

	@PostMapping("/tickets")
	public ResponseEntity<CreateTicketResponse> createTicket(@RequestBody CreateTicketRequest createTicketRequest){
		
		CreateTicketResponse createTicketResponse= ticketService.createTicket(createTicketRequest);
		return  ResponseEntity.status(HttpStatus.CREATED).body(createTicketResponse);
	}
	
	@PutMapping("/tickets/{id}")
	public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id , @RequestBody UpdateTicketRequest updateTicketRequest ){
		TicketResponse ticketResponse = ticketService.updateTicket(id, updateTicketRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketResponse);
	}
	
	@DeleteMapping("/tickets/{id}")
	public ResponseEntity<Boolean> deleteTicket(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.deleteTicket(id));
	}
	
}
