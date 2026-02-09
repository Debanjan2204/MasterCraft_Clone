package com.ded.BTS.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.Project;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.User;


// User
public interface TicketRepo extends JpaRepository<Ticket, Long> {
	
	public List<Ticket> findByProject(Project project);
	public List<Ticket> findByAssignee(User assignee);
	public List<Ticket> findByReporter(User reporter);
	
}