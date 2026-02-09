package com.ded.BTS.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.UserRole;

// User
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {}