package com.ded.BTS.repository;
import java.util.List;
import com.ded.BTS.enums.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ded.BTS.model.Role;

// User
public interface RoleRepo extends JpaRepository<Role, Long> {
	
	List<Role> findByNameIn(List<RoleNames> names);
}