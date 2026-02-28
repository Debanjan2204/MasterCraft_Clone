package com.ded.BTS.enums;



import com.ded.BTS.Exceptions.InvalidEnumException;

public enum TicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;
	public static TicketPriority getvalueOf(String value) {

		try {
			return TicketPriority.valueOf(value.toUpperCase());
		}catch (IllegalArgumentException ex) {
	        throw new InvalidEnumException(
	        		TicketPriority.class,
	                value
	        );
		
		}
		
	}
}