package com.ded.BTS.service;

import java.time.Instant;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
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
import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.Project;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.TicketComment;
import com.ded.BTS.model.User;
import com.ded.BTS.repository.ProjectRepo;
import com.ded.BTS.repository.TicketCommentRepo;
import com.ded.BTS.repository.TicketRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.security.model.CurrentUser;

import jakarta.persistence.EntityNotFoundException;
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
	private final ApplicationEventPublisher applicationEventPublisher;





	public TicketService(TicketRepo ticketRepo, UserRepo userRepo, TicketCommentRepo ticketCommentRepo,
			ProjectRepo projectRepo, CreateTicketRequestMapper createTicketRequestMapper,
			CreateTicketResponseMapper createTicketResponseMapper, UpdateTicketRequestMapper updateTicketRequestMapper,
			TicketResponseMapper ticketResponseMapper, AddCommentRequestMapper addCommentRequestMapper,
			TicketCommentResponseMapper ticketCommentResponseMapper, CurrentUser currentUser,
			ApplicationEventPublisher applicationEventPublisher) {
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
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional
	public CreateTicketResponse createTicket(CreateTicketRequest ticketRequest) {
		Project project = projectRepo.findById(ticketRequest.projectId())
				.orElseThrow(()-> new EntityNotFoundException("Project with id "+ticketRequest.projectId()+" not found"));
		User assignee = userRepo.findByUsername(ticketRequest.assigneeUserName(),UserStatus.ACTIVE)
				.orElseThrow(()-> new EntityNotFoundException("Assignee with username "+ticketRequest.assigneeUserName()+" not found"));
		User reporter = userRepo.findByUsername(currentUser.getLoggedInUserId(),UserStatus.ACTIVE)
				.orElseThrow(()-> new EntityNotFoundException("Reporter with id "+currentUser.getLoggedInUserId()+" not found"));
		Ticket ticket = createTicketRequestMapper.toEntity(ticketRequest);
		ticket.setProject(project);
		ticket.setAssignee(assignee);
		ticket.setReporter(reporter);
		ticketRepo.save(ticket);
		applicationEventPublisher.publishEvent(ticket);
		return createTicketResponseMapper.toResponse(ticket);
	}

	@Transactional
	public TicketResponse updateTicket(Long ticketId, UpdateTicketRequest updateTicketRequest) {
		Ticket oldTicket = ticketRepo.findById(ticketId)
				.orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found"));
		updateTicketRequestMapper.updateTicketFromDto(updateTicketRequest, oldTicket);
		ticketRepo.save(oldTicket);
		applicationEventPublisher.publishEvent(oldTicket);
		return ticketResponseMapper.toResponse(oldTicket);
	}

	@Transactional
	public TicketResponse assignTicket(Long ticketId, String newAssigneeUserName) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket Not Found"));
		User newAssigneeUser = userRepo.findByUsername(newAssigneeUserName,UserStatus.ACTIVE)
				.orElseThrow(()-> new EntityNotFoundException("User with username "+newAssigneeUserName+" not found"));
		ticket.setAssignee(newAssigneeUser);
		ticket.setUpdatedAt(Instant.now());
		ticketRepo.save(ticket);
		applicationEventPublisher.publishEvent(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	@Transactional
	public TicketResponse changeTicketStatus(Long ticketId, String newStatus) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found"));
		ticket.setStatus(TicketStatus.getvalueOf(newStatus.toUpperCase()));
		ticketRepo.save(ticket);
		applicationEventPublisher.publishEvent(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	@Transactional
	public TicketResponse setPriority(Long ticketId, String newPriority) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found"));
		ticket.setPriority(TicketPriority.getvalueOf(newPriority.toUpperCase()));
		ticketRepo.save(ticket);
		applicationEventPublisher.publishEvent(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	@Transactional
	public TicketResponse setDueDate(Long ticketId, Instant newDueDate) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found"));
		ticket.setDueDate(newDueDate);
		ticket.setUpdatedAt(Instant.now());
		ticketRepo.save(ticket);
		applicationEventPublisher.publishEvent(ticket);
		return ticketResponseMapper.toResponse(ticket);
	}

	public List<TicketResponse> getAllTickets() {
		return ticketResponseMapper.toResponseList(ticketRepo.findAllByOrderByIdAsc());

	}

	public TicketResponse getTicketById(Long ticketId) {

		return ticketResponseMapper
				.toResponse(ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found")));
	}

	public List<TicketResponse> getTicketsByProject(Long projectId) {
		Project project = projectRepo.findById(projectId).orElseThrow(()-> new EntityNotFoundException("Project with id "+projectId+" not found"));

		return ticketResponseMapper.toResponseList(ticketRepo.findByProject(project));
	}

	public List<TicketResponse> getTicketsByAssignee(String assigneeUserName) {
		User assignee = userRepo.findByUsername(assigneeUserName,UserStatus.ACTIVE).orElseThrow(() -> new EntityNotFoundException(
				"Assignee with username " + assigneeUserName + " not found"));

		return ticketResponseMapper.toResponseList(ticketRepo.findByAssignee(assignee));
	}

	public List<TicketResponse> getTicketsByReporter() {
		User reporter = userRepo.findByUsername(currentUser.getLoggedInUserId(),UserStatus.ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException(
						"Reporter with username " + currentUser.getLoggedInUserId() + " not found"));
		return ticketResponseMapper.toResponseList(ticketRepo.findByReporter(reporter));
	}

	@Transactional
	public TicketCommentResponse addComment(Long ticketId, AddCommentRequest addCommentRequest) {
		User author = userRepo.findByUsername(currentUser.getLoggedInUserId(),UserStatus.ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException(
						"Author with username " + currentUser.getLoggedInUserId() + " not found"));
		TicketComment ticketComment = addCommentRequestMapper.toEntity(addCommentRequest);
		ticketComment.setTicket(ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found")));
		ticketComment.setAuthor(author);
		return ticketCommentResponseMapper.toResponse(ticketCommentRepo.save(ticketComment));
	}

	public List<TicketCommentResponse> getCommentsByTicket(Long ticketId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found"));

		return ticketCommentResponseMapper.toResponseList(ticketCommentRepo.findByTicket(ticket));
	}

	@Transactional
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Boolean deleteTicket(Long ticketId) {
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new EntityNotFoundException("Ticket Number "+ticketId+" not found"));
		ticket.setRecEndDate(Instant.now());
		ticketRepo.save(ticket);
		applicationEventPublisher.publishEvent(ticket);
		return Boolean.TRUE;
	}

}
