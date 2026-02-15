package com.ded.BTS.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ded.BTS.model.User;

// User
public interface UserRepo extends JpaRepository<User, Long> {
	
	
	
	@Query("""
		    SELECT u FROM User u
		    LEFT JOIN FETCH u.userRoles ur
		    LEFT JOIN FETCH ur.role
		    WHERE u.username = :username
		""")
		Optional<User> findByUsernameWithRoles(@Param("username") String username);
		
		Optional<User> findByUsername(String username);
		boolean existsByUsername(String username);
		boolean existsByEmail(String email);
}