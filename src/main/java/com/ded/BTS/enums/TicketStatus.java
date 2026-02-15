package com.ded.BTS.enums;

public enum TicketStatus {
    PENDING_IT,
    PENDING_USER,
    FIXED_IN_BAT,
    CLOSED,
    REOPEN,
    IN_PROGRESS;
	public static TicketStatus getvalueOf(String value) {
		try {
			return TicketStatus.valueOf(value.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
