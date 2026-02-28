package com.ded.BTS.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.User;

// User
public interface UserRepo extends JpaRepository<User, Long> {
	
	
	
	@Query("""
		    SELECT u FROM User u
		    LEFT JOIN FETCH u.userRoles ur
		    LEFT JOIN FETCH ur.role
		    WHERE u.username = :username
		    AND u.status = :status
		""")
		Optional<User> findByUsernameWithRoles(@Param("username") String username, @Param("status") UserStatus status);
		
	
	@Query("""
		    SELECT u FROM User u
		    WHERE u.username = :username
		    AND u.status = :status
		""")
		Optional<User> findByUsername(@Param("username") String username, @Param("status") UserStatus status);
	
	
	@Query("""
		    SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
		    FROM User u
		    WHERE u.username = :username
		    AND u.status = :status
		""")
		boolean existsByUsername(@Param("username") String username, @Param("status") UserStatus status);
	
	
	@Query("""
		    SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
		    FROM User u
		    WHERE u.email = :email
		    AND u.status = :status
		""")
		boolean existsByEmail(@Param("email") String email, @Param("status") UserStatus status);
}