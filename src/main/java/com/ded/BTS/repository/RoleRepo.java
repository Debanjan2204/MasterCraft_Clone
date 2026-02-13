package com.ded.BTS.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ded.BTS.model.Role;

// User
public interface RoleRepo extends JpaRepository<Role, Long> {
	
	@Query("select r from Role r where r.name = :name")
	List<Role> findByName(@Param("name") String name);
	
}