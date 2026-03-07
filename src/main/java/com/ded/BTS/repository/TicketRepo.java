package com.ded.BTS.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ded.BTS.model.Project;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.User;
import com.ded.BTS.enums.TicketPriority;
import com.ded.BTS.enums.TicketStatus;



// User
public interface TicketRepo extends JpaRepository<Ticket, Long> {
	
	public List<Ticket> findAllByOrderByIdAsc();
	public List<Ticket> findByProject(Project project);
	public List<Ticket> findByAssignee(User assignee);
	public List<Ticket> findByReporter(User reporter);
	
	@Query("""
			SELECT t FROM Ticket t
			WHERE t.status = :status
			OR t.priority = :priority
			OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))
			AND t.recEndDate = TO_DATE('01019999','DDMMYYYY')
			""")
	 List<Ticket> findByStatusOrPriorityOrTitle(@Param("status") TicketStatus status,@Param("priority") TicketPriority priority,@Param("title") String title);
	
}