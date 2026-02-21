package com.ded.BTS.beans;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.TicketHistory;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserSummary;
import com.ded.BTS.repository.TicketHistoryRepo;
import com.ded.BTS.security.model.CurrentUser;


@Component
public class TicketEventListener {

    
    private final TicketHistoryRepo historyRepo;
    private final CurrentUser currentUser;
    
    
    
    public TicketEventListener(TicketHistoryRepo historyRepo, CurrentUser currentUser) {
		super();
		this.historyRepo = historyRepo;
		this.currentUser = currentUser;
	}

	@EventListener
	public void auditTicket(Ticket ticket) {
	    Map<String, Object> oldState = ticket.getOriginalState();
	    // Fields to audit
	    for (String field : oldState.keySet()) {
	        Object oldValue = oldState.get(field);
	        Object newValue = getFieldValueReflectively(ticket, field);
	        if (!Objects.equals(oldValue, newValue)) {
	            logFieldChange(ticket, field, oldValue, newValue);
	        }
	    }
	}

	// Utility to get field values dynamically
	private Object getFieldValueReflectively(Ticket ticket, String fieldName) {
	    try {
	        Field field = getFieldRecursively(Ticket.class, fieldName);
	        return field.get(ticket);
	    } catch (NoSuchFieldException | IllegalAccessException e) {
	        throw new RuntimeException("Failed to get value for field: " + fieldName, e);
	    }
	}
	
	private Field getFieldRecursively(Class<?> clazz, String fieldName) throws NoSuchFieldException {
	    Class<?> current = clazz;
	    while (current != null) {
	        try {
	            Field field = current.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            return field;
	        } catch (NoSuchFieldException e) {
	            current = current.getSuperclass(); // go up
	        }
	    }
	    throw new NoSuchFieldException(fieldName + " not found in class hierarchy");
	}
	    
//	private Object getFieldValue(Ticket ticket, String fieldName) {
//	    return switch (fieldName) {
//	        case "project" -> ticket.getProject();
//	        case"title" -> ticket.getTitle();
//	        case"description" -> ticket.getDescription();
//	        case"type" -> ticket.getType();
//	        case "priority" -> ticket.getPriority();
//	        case"status" -> ticket.getStatus();
//	        case "assignee" -> ticket.getAssignee();
//	        case  "dueDate"  -> ticket.getDueDate();
//	        case"recEndDate"-> ticket.getRecEndDate();
//	        default -> null;
//	    };
//	}

    private void logFieldChange(Ticket ticket, String field, Object oldValue, Object newValue) {
    	// Map User objects to UserSummary if needed
        if (oldValue instanceof User) oldValue = UserSummary.from((User)oldValue);
        if (newValue instanceof User) newValue = UserSummary.from((User)newValue);
        TicketHistory history = new TicketHistory(ticket, field, oldValue, newValue, currentUser.getLoggedInUserId(), Instant.now());
        historyRepo.save(history);
    }
}
