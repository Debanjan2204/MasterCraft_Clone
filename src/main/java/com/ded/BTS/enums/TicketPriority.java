package com.ded.BTS.enums;

public enum TicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;
	public static TicketPriority getvalueOf(String value) {
		try {
			return TicketPriority.valueOf(value.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}