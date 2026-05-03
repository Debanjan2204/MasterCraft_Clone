package com.ded.BTS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.TicketAiSummary;
import com.ded.BTS.model.TicketSummaryId;

public interface TicketAiSummaryRepo extends JpaRepository<TicketAiSummary, TicketSummaryId> {

	Optional<TicketAiSummary> findByTicketIdAndContentHash(Long ticketIdLong , String contentHash);
}
