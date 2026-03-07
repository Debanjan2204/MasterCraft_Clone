package com.ded.BTS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ded.BTS.model.Attachment;
import java.util.List;


@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long>{

	List<Attachment> findByEntityTypeAndEntityId(String entityType, Long entityId);
}
