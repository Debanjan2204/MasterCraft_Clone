package com.ded.BTS.model;

import java.time.Instant;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "old_value", length = 5000)
    private String oldValue;

    @Column(name = "new_value", length = 5000)
    private String newValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    /* No setters - constructor-based immutability */
    protected TicketHistory() {
        // Required by JPA
    }

    public TicketHistory(Ticket ticket, String fieldChanged, String oldValue, String newValue, User changedBy, Instant changedAt) {
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

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public Instant getChangedAt() {
        return changedAt;
    }
}
