package com.ded.BTS.enums;

public enum TicketType {
	DEFECT, BTS, STORY;

	public static TicketType getvalueOf(String value) {
		try {
			return TicketType.valueOf(value.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
