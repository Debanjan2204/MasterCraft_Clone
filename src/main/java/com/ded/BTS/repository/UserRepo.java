package com.ded.BTS.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.User;

// User
public interface UserRepo extends JpaRepository<User, Long> {}