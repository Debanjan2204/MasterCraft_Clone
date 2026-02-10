package com.ded.BTS.service;

import java.time.Instant;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.ded.BTS.DTO.request.AddCommentRequest;
import com.ded.BTS.DTO.request.CreateTicketRequest;
import com.ded.BTS.DTO.request.UpdateTicketRequest;
import com.ded.BTS.DTO.request.mapper.AddCommentRequestMapper;
import com.ded.BTS.DTO.request.mapper.CreateTicketRequestMapper;
import com.ded.BTS.DTO.request.mapper.UpdateTicketRequestMapper;
import com.ded.BTS.DTO.response.CreateTicketResponse;
import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.DTO.response.mapper.CreateTicketResponseMapper;
import com.ded.BTS.DTO.response.mapper.TicketCommentResponseMapper;
import com.ded.BTS.DTO.response.mapper.TicketResponseMapper;
import com.ded.BTS.enums.TicketPriority;
import com.ded.BTS.enums.TicketStatus;
import com.ded.BTS.model.Project;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.TicketComment;
import com.ded.BTS.model.User;
import com.ded.BTS.repository.ProjectRepo;
import com.ded.BTS.repository.TicketCommentRepo;
import com.ded.BTS.repository.TicketRepo;
import com.ded.BTS.repository.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class TicketService{

	private final TicketRepo ticketRepo;
	private final UserRepo userRepo;
	private final TicketCommentRepo ticketCommentRepo;
	private final ProjectRepo projectRepo;
	private final CreateTicketRequestMapper createTicketRequestMapper;
	private final CreateTicketResponseMapper createTicketResponseMapper;
	private final UpdateTicketRequestMapper updateTicketRequestMapper;
	private final TicketResponseMapper ticketResponseMapper;
	private final AddCommentRequestMapper addCommentRequestMapper;
	private final TicketCommentResponseMapper ticketCommentResponseMapper;
	
	public TicketService(TicketRepo ticketRepo, UserRepo userRepo, TicketCommentRepo ticketCommentRepo,
			ProjectRepo projectRepo, CreateTicketRequestMapper createTicketRequestMapper,
			CreateTicketResponseMapper createTicketResponseMapper, UpdateTicketRequestMapper updateTicketRequestMapper,
			TicketResponseMapper ticketResponseMapper, AddCommentRequestMapper addCommentRequestMapper,TicketCommentResponseMapper ticketCommentResponseMapper) {
		super();
		this.ticketRepo = ticketRepo;
		this.userRepo = userRepo;
		this.ticketCommentRepo = ticketCommentRepo;
		this.projectRepo = projectRepo;
		this.createTicketRequestMapper = createTicketRequestMapper;
		this.createTicketResponseMapper = createTicketResponseMapper;
		this.updateTicketRequestMapper = updateTicketRequestMapper;
		this.ticketResponseMapper = ticketResponseMapper;
		this.addCommentRequestMapper=addCommentRequestMapper;
		this.ticketCommentResponseMapper=ticketCommentResponseMapper;
		}

	 
	@Transactional
	public CreateTicketResponse createTicket(CreateTicketRequest ticketRequest) {
		Project project = projectRepo.findById(ticketRequest.projectId())
				.orElseThrow(() -> new ObjectNotFoundException(Project.class, ticketRequest.projectId().toString()));
		User assignee = userRepo.findById(ticketRequest.assigneeId())
				.orElseThrow(() -> new ObjectNotFoundException(User.class, ticketRequest.assigneeId().toString()));
		User reporter = userRepo.findById(ticketRequest.reporterId())
				.orElseThrow(() -> new ObjectNotFoundException(User.class, ticketRequest.reporterId().toString()));
		Ticket ticket = createTicketRequestMapper.toEntity(ticketRequest);
		ticket.setProject(project);
		ticket.setAssignee(assignee);
		ticket.setReporter(reporter);
		ticketRepo.save(ticket);
		return createTicketResponseMapper.toResponse(ticket);
	}

	 
	@Transactional
	public TicketResponse updateTicket(Long ticketId, UpdateTicketRequest updateTicketRequest) {
		Ticket oldTicket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		updateTicketRequestMapper.updateTicketFromDto(updateTicketRequest, oldTicket);
		ticketRepo.save(oldTicket);
		return ticketResponseMapper.toResponse(oldTicket);
	}

	 
	@Transactional
	public TicketResponse assignTicket(Long ticketId, Long newAssigneeId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		User newAssigneeUser = userRepo.findById(newAssigneeId).orElseThrow(() -> new RuntimeException());
		ticket.setAssignee(newAssigneeUser);
		ticketRepo.save(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	 
	@Transactional
	public TicketResponse changeTicketStatus(Long ticketId, String newStatus) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		ticket.setStatus(TicketStatus.getvalueOf(newStatus.toUpperCase()));
		ticketRepo.save(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	 
	@Transactional
	public TicketResponse setPriority(Long ticketId, String newPriority) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		ticket.setPriority(TicketPriority.getvalueOf(newPriority.toUpperCase()));
		ticketRepo.save(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	 
	@Transactional
	public TicketResponse setDueDate(Long ticketId, Instant newDueDate) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		ticket.setDueDate(newDueDate);
		ticketRepo.save(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	 public List<TicketResponse> getAllTickets(){
		 return ticketResponseMapper.toResponseList(ticketRepo.findAll());
	 }
	
	public TicketResponse getTicketById(Long ticketId) {

		return ticketResponseMapper.toResponse(ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket Not Found")));
	}

	 
	public List<TicketResponse> getTicketsByProject(Long projectId) {
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException());
		return ticketResponseMapper.toResponseList(ticketRepo.findByProject(project));
	}

	 
	public List<TicketResponse> getTicketsByAssignee(Long assigneeId) {
		User assignee = userRepo.findById(assigneeId).orElseThrow(() -> new RuntimeException());
		return ticketResponseMapper.toResponseList(ticketRepo.findByAssignee(assignee));
	}

	 
	public List<TicketResponse> getTicketsByReporter(Long reporterId) {
		User reporter = userRepo.findById(reporterId).orElseThrow(() -> new RuntimeException());
		return ticketResponseMapper.toResponseList(ticketRepo.findByReporter(reporter));
	}

	 
	@Transactional
	public TicketCommentResponse addComment(Long ticketId, AddCommentRequest addCommentRequest) {
		User author= userRepo.findById(addCommentRequest.authorId())
				.orElseThrow(() -> new ObjectNotFoundException(User.class, addCommentRequest.authorId().toString()));
		TicketComment ticketComment= addCommentRequestMapper.toEntity(addCommentRequest);
		ticketComment.setTicket(ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException()));
		ticketComment.setAuthor(author);
		return ticketCommentResponseMapper.toResponse( ticketCommentRepo.save(ticketComment));
	}

	 
	public List<TicketCommentResponse> getCommentsByTicket(Long ticketId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());

		return ticketCommentResponseMapper.toResponseList (ticketCommentRepo.findByTicket(ticket));
	}


	@Transactional
	public Boolean deleteTicket(Long ticketId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		ticket.setRecEndDate(Instant.now());
		ticketRepo.save(ticket);
		return Boolean.TRUE;
	}

}
