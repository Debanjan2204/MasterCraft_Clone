package com.ded.BTS.repository;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ded.BTS.model.UserRole;

// User
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
	@Modifying
	@Query("""
			UPDATE UserRole ur
			SET ur.status = 'APPROVED'
			WHERE ur.id = :userRoleId
			AND ur.user.id= :userId
		
		""")
	public int approveRole(@Param("userId") Long userId,@Param("userRoleId") Long userRoleId);
	
	@Modifying
	@Query("""
			UPDATE UserRole ur
			SET ur.status = 'REJECTED'
			WHERE ur.id = :userRoleId
			AND ur.user.id= :userId
		
		""")
	public int rejectRole(@Param("userId") Long userId,@Param("userRoleId") Long userRoleId);
	
	
}