package com.ded.BTS.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.TicketComment;


// User
public interface TicketCommentRepo extends JpaRepository<TicketComment, Long> {
	
	public List<TicketComment> findByTicket(Ticket ticket);
}