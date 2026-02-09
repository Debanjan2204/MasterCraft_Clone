package com.ded.BTS.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.Project;

// User
public interface ProjectRepo extends JpaRepository<Project, Long> {}