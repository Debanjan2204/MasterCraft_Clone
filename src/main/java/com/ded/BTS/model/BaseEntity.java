package com.ded.BTS.model;

import java.time.Instant;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
@SQLRestriction("rec_end_date = high_date()")
public abstract class BaseEntity {

    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @Column(name = "created_at", updatable = false)
    protected Instant createdAt;

    @Column(name = "updated_by")
    protected String updatedBy;

    @Column(name = "updated_at")
    protected Instant updatedAt;

    @Column(name = "rec_start_date", updatable = false)
    protected Instant recStartDate;

    @Column(name = "rec_end_date")
    protected Instant recEndDate;

    /* getters & setters */

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getRecStartDate() {
        return recStartDate;
    }

    public void setRecStartDate(Instant recStartDate) {
        this.recStartDate = recStartDate;
    }

    public Instant getRecEndDate() {
        return recEndDate;
    }

    public void setRecEndDate(Instant recEndDate) {
        this.recEndDate = recEndDate;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.recStartDate = now;
        this.recEndDate = Instant.parse("9999-01-01T00:00:00Z");

        // TEMP for local testing
        this.createdBy = "1L";
    }

//    @PreUpdate
//    protected void onUpdate(String updatedBy) {
//        this.updatedAt = Instant.now();
//
//        // TEMP for local testing
//        this.updatedBy = updatedBy;
//    }

}
