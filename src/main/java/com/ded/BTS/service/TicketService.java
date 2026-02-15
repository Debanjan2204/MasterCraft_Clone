package com.ded.BTS.service;

import java.time.Instant;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ded.BTS.security.model.CurrentUser;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

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
	private final CurrentUser currentUser;

	public TicketService(TicketRepo ticketRepo, UserRepo userRepo, TicketCommentRepo ticketCommentRepo,
			ProjectRepo projectRepo, CreateTicketRequestMapper createTicketRequestMapper,
			CreateTicketResponseMapper createTicketResponseMapper, UpdateTicketRequestMapper updateTicketRequestMapper,
			TicketResponseMapper ticketResponseMapper, AddCommentRequestMapper addCommentRequestMapper,
			TicketCommentResponseMapper ticketCommentResponseMapper, CurrentUser currentUser) {
		super();
		this.ticketRepo = ticketRepo;
		this.userRepo = userRepo;
		this.ticketCommentRepo = ticketCommentRepo;
		this.projectRepo = projectRepo;
		this.createTicketRequestMapper = createTicketRequestMapper;
		this.createTicketResponseMapper = createTicketResponseMapper;
		this.updateTicketRequestMapper = updateTicketRequestMapper;
		this.ticketResponseMapper = ticketResponseMapper;
		this.addCommentRequestMapper = addCommentRequestMapper;
		this.ticketCommentResponseMapper = ticketCommentResponseMapper;
		this.currentUser = currentUser;
	}

	@Transactional
	public CreateTicketResponse createTicket(CreateTicketRequest ticketRequest) {
		Project project = projectRepo.findById(ticketRequest.projectId())
				.orElseThrow(() -> new ObjectNotFoundException(Project.class, ticketRequest.projectId().toString()));
		User assignee = userRepo.findByUsername(ticketRequest.assigneeUserName())
				.orElseThrow(() -> new ObjectNotFoundException(User.class, ticketRequest.assigneeUserName()));
		User reporter = userRepo.findByUsername(currentUser.getLoggedInUserId())
				.orElseThrow(() -> new ObjectNotFoundException(User.class, currentUser.getLoggedInUserId().toString()));
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
	public TicketResponse assignTicket(Long ticketId, String newAssigneeUserName) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket Not Found"));
		User newAssigneeUser = userRepo.findByUsername(newAssigneeUserName)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		ticket.setAssignee(newAssigneeUser);
		ticket.setUpdatedAt(Instant.now());
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
		ticket.setUpdatedAt(Instant.now());
		ticketRepo.save(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	public List<TicketResponse> getAllTickets() {
		return ticketResponseMapper.toResponseList(ticketRepo.findAll());

	}

	public TicketResponse getTicketById(Long ticketId) {

		return ticketResponseMapper
				.toResponse(ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket Not Found")));
	}

	public List<TicketResponse> getTicketsByProject(Long projectId) {
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException());
		return ticketResponseMapper.toResponseList(ticketRepo.findByProject(project));
	}

	public List<TicketResponse> getTicketsByAssignee(String assigneeUserName) {
		User assignee = userRepo.findByUsername(assigneeUserName).orElseThrow(() -> new RuntimeException());
		return ticketResponseMapper.toResponseList(ticketRepo.findByAssignee(assignee));
	}

	public List<TicketResponse> getTicketsByReporter() {
		User reporter = userRepo.findByUsername(currentUser.getLoggedInUserId())
				.orElseThrow(() -> new RuntimeException());
		return ticketResponseMapper.toResponseList(ticketRepo.findByReporter(reporter));
	}

	@Transactional
	public TicketCommentResponse addComment(Long ticketId, AddCommentRequest addCommentRequest) {
		User author = userRepo.findByUsername(currentUser.getLoggedInUserId())
				.orElseThrow(() -> new ObjectNotFoundException(User.class, currentUser.getLoggedInUserId()));
		TicketComment ticketComment = addCommentRequestMapper.toEntity(addCommentRequest);
		ticketComment.setTicket(ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException()));
		ticketComment.setAuthor(author);
		return ticketCommentResponseMapper.toResponse(ticketCommentRepo.save(ticketComment));
	}

	public List<TicketCommentResponse> getCommentsByTicket(Long ticketId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());

		return ticketCommentResponseMapper.toResponseList(ticketCommentRepo.findByTicket(ticket));
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Boolean deleteTicket(Long ticketId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException());
		ticket.setRecEndDate(Instant.now());
		ticketRepo.save(ticket);
		return Boolean.TRUE;
	}

}
