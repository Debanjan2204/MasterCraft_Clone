package com.ded.BTS.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.TicketHistory;

// User
public interface TicketHistoryRepo extends JpaRepository<TicketHistory, Long> {}