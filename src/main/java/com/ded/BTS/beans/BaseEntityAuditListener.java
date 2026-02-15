package com.ded.BTS.beans;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ded.BTS.constants.GlobalConstants;
import com.ded.BTS.model.BaseEntity;
import com.ded.BTS.security.model.CurrentUser;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Component
public class BaseEntityAuditListener {

    private static CurrentUser currentUser;

    @Autowired
    public void setCurrentUser(CurrentUser currentUser) {
        BaseEntityAuditListener.currentUser = currentUser;
    }

    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreatedBy(currentUser.getLoggedInUserId());
        Instant nowInstant =Instant.now();
        Instant rec_end_dateInstant=Instant.parse(GlobalConstants.INSTANT_HIGH_DATE);
        entity.setRecStartDate(nowInstant);
        entity.setCreatedAt(nowInstant);
        entity.setRecEndDate(rec_end_dateInstant);
        
        
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdatedBy(currentUser.getLoggedInUserId());
        entity.setUpdatedAt(Instant.now());
    }
}

