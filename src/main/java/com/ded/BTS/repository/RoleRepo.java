package com.ded.BTS.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.Role;

// User
public interface RoleRepo extends JpaRepository<Role, Long> {}