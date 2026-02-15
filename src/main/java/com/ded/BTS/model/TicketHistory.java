package com.ded.BTS.model;

import java.time.Instant;

import com.ded.BTS.beans.ObjectToJsonConverter;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_history")
@Access(AccessType.FIELD) // JPA will set fields via reflection
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_seq")
    @SequenceGenerator(name = "history_seq", sequenceName = "ticket_hist_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "field_changed", nullable = false)
    private String fieldChanged;

    @Convert(converter = ObjectToJsonConverter.class)
    @Column(name = "old_value")
    private Object oldValue;

    @Convert(converter = ObjectToJsonConverter.class)
    @Column(name = "new_value")
    private Object newValue;

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    /* No setters - constructor-based immutability */
    public TicketHistory() {
        // Required by JPA
    }

    public TicketHistory(Ticket ticket, String fieldChanged, Object oldValue, Object newValue, String changedBy, Instant changedAt) {
        this.ticket = ticket;
        this.fieldChanged = fieldChanged;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
    }

    /* Getters only */
    public Long getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getFieldChanged() {
        return fieldChanged;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public Instant getChangedAt() {
        return changedAt;
    }
}
