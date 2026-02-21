package com.ded.BTS.enums;

import com.ded.BTS.Exceptions.InvalidEnumException;

public enum TicketStatus {
	POOL, PENDING_IT, PENDING_USER, FIXED_IN_BAT, CLOSED, REOPEN, IN_PROGRESS;

	public static TicketStatus getvalueOf(String value) {
		try {
			return TicketStatus.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new InvalidEnumException(TicketStatus.class, value);
		}
	}
}
