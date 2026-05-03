package com.ded.BTS.model;

import java.io.Serializable;

public class TicketSummaryId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long ticketId;
    private String contentHash;
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
	public TicketSummaryId(Long ticketId, String contentHash) {
		super();
		this.ticketId = ticketId;
		this.contentHash = contentHash;
	}
	public TicketSummaryId() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}