package com.ded.BTS.model;

import jakarta.persistence.*;
import java.time.Instant;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.networknt.schema.utils.JsonType;

@Entity
@Table(name = "ticket_ai_summary")
@IdClass(TicketSummaryId.class)
@SQLRestriction("rec_end_date = high_date()")
public class TicketAiSummary extends BaseEntity{

    @Id
    private Long ticketId;

    @Id
    private String contentHash;

    @Type(JsonType.class)
    @Column(nullable = false, columnDefinition = "jsonb")
    private String summaryJson;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getContentHash() {
		return contentHash;
	}

	public void setContentHash(String contentHash) {
		this.contentHash = contentHash;
	}

	public String getSummaryJson() {
		return summaryJson;
	}

	public void setSummaryJson(String summaryJson) {
		this.summaryJson = summaryJson;
	}

	public TicketAiSummary(Long ticketId, String contentHash, String summaryJson) {
		super();
		this.ticketId = ticketId;
		this.contentHash = contentHash;
		this.summaryJson = summaryJson;
	}

	public TicketAiSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
}