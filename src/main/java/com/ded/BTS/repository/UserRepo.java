package com.ded.BTS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ded.BTS.enums.RoleNames;
import com.ded.BTS.enums.RoleStatus;
import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.User;

// User
public interface UserRepo extends JpaRepository<User, Long> {

	@Query("""
			    SELECT u FROM User u
			    LEFT JOIN FETCH u.userRoles ur
			    LEFT JOIN FETCH ur.role
			    WHERE ur.status = :roleStatus
			    
			""")
	List<User> findAllUserDetails(@Param("roleStatus") RoleStatus roleStatus);

	@Query("""
			    SELECT DISTINCT u FROM User u
			    LEFT JOIN FETCH u.userRoles ur
			    LEFT JOIN FETCH ur.role
			    WHERE u.username = :username
			    AND u.status = :user_status
			""")
	Optional<User> findAuthUser(@Param("username") String username, @Param("user_status") UserStatus user_status);

	@Query("""
			    SELECT u FROM User u
			    WHERE u.username = :username
			    AND u.status = :status
			""")
	Optional<User> findByUsername(@Param("username") String username, @Param("status") UserStatus status);

	Optional<User> findByUsername(String username);

	List<User> findAllByStatus(UserStatus status);

	@Query("""
			    SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
			    FROM User u
			    WHERE u.username = :username
			    AND u.status = :status
			""")
	boolean existsByUsername(@Param("username") String username, @Param("status") UserStatus status);

	boolean existsByUsername(String username);

	@Query("""
			    SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
			    FROM User u
			    WHERE u.email = :email
			    AND u.status = :status
			""")
	boolean existsByEmail(@Param("email") String email, @Param("status") UserStatus status);

	boolean existsByEmail(String email);
	
	@Modifying
	@Query("""
		UPDATE User u
		SET u.status = 'ACTIVE'
		WHERE u.id = :userId
			""")
	public int approveUser(@Param("userId") Long userId);
}