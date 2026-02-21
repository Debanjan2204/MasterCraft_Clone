package com.ded.BTS.enums;

import com.ded.BTS.Exceptions.InvalidEnumException;

public enum TicketType {
	DEFECT, BTS, STORY;

	public static TicketType getvalueOf(String value) {
		try {
			return TicketType.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException ex) {
	        throw new InvalidEnumException(
	        		TicketType.class,
	                value
	        );
		}
		
	}
}
