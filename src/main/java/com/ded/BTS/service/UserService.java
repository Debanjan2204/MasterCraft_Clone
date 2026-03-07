package com.ded.BTS.service;

import java.io.ObjectInputStream.GetField;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.ded.BTS.DTO.response.mapper.PendingUserSummaryMapper;
import com.ded.BTS.Exceptions.AuthorizationException;
import com.ded.BTS.Exceptions.RoleAlreadyAssignedException;
import com.ded.BTS.Exceptions.RoleNotFoundException;
import com.ded.BTS.beans.*;
import com.ded.BTS.enums.RoleNames;
import com.ded.BTS.enums.RoleStatus;
import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.ApproveUserDetailRequest;
import com.ded.BTS.model.PendingUserDetail;
import com.ded.BTS.model.Role;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserRole;
import com.ded.BTS.model.UserSummary;
import com.ded.BTS.repository.RoleRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.repository.UserRoleRepo;
import com.ded.BTS.security.model.CurrentUser;
import com.ded.BTS.security.model.RegisterRequest;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class UserService {


    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;
    private final PendingUserSummaryMapper pendingUserSummaryMapper;
    private final UserRoleRepo userRoleRepo;
    
    
    public UserService(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder,CurrentUser currentUser,PendingUserSummaryMapper pendingUserSummaryMapper, UserRoleRepo userRoleRepo) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.currentUser=currentUser;
		this.pendingUserSummaryMapper=pendingUserSummaryMapper;
		this.userRoleRepo = userRoleRepo;
	}





	private Boolean isAdmin() {
		if (currentUser.getLoggedInUser() != null
				&& currentUser.getLoggedInUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			return true;

		} else {
			return false;
		}
	}
	public String createUser(RegisterRequest request) {
		List<RoleNames> addRoles= request.roles().stream().map( role-> RoleNames.getvalueOf(role)).toList();
		
		String retString=null;
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        if(isAdmin())
        {
        	user.setStatus(UserStatus.ACTIVE);
        	retString="ACTIVE";
        }
        else {
        	user.setStatus(UserStatus.UNVERIFIED);
        }
        
        List<Role> rolesFromDb =
                roleRepository.findByNameIn(addRoles);

        Set<RoleNames> foundRoles = rolesFromDb.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        List<RoleNames> missingRoles = addRoles
                .stream()
                .filter(role -> !foundRoles.contains(role))
                .toList();
        
        if (!missingRoles.isEmpty()) {
            throw new RoleNotFoundException(missingRoles);
        }

        for( Role role :rolesFromDb) {
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            if (isAdmin()) {
            	userRole.setStatus(RoleStatus.APPROVED);
			}
            else {
            	userRole.setStatus(RoleStatus.REQUESTED);
            }
            user.addUserRole(userRole); // sets both sides
        }

        userRepository.save(user);
        return retString;
    }
	
	public List<PendingUserDetail> getPendingAuthorizationUsers() {
		return pendingUserSummaryMapper.toDtoList(userRepository.findAllUserDetails(RoleStatus.REQUESTED));
	}
	
	@Transactional
	public String authorizeUser(ApproveUserDetailRequest approveUserDetailRequest) {
		int userRowsUpdated;
		int approveRoleRowsUpdated;
		int rejectRoleRowsUpdated;
		String returnString ="\n";
		Map<Long, RoleStatus> rolePerMap = approveUserDetailRequest.userRolePermMap();		
		List<UserRole> userRoles= userRoleRepo.findAllById(rolePerMap.keySet());
		if (userRoles.size() != rolePerMap.size()) {
		    throw new EntityNotFoundException("One or more UserRoles not found");
		}

		boolean adminRolePresent = userRoles.stream()
		        .anyMatch(r -> r.getRole().getNameString().equalsIgnoreCase("ROLE_ADMIN"));

		if (adminRolePresent) {
		    throw new AuthorizationException(
		            "Admin cannot approve admin access. Please contact superadmin for access",
		            null);
		}
		
		if(approveUserDetailRequest.toBeUpdatedUserStatus()==UserStatus.getvalueOf("ACTIVE")) {
			userRowsUpdated = userRepository.approveUser(approveUserDetailRequest.userId());
			System.out.println(userRowsUpdated+" rows updated in User table");
			returnString+=(String.valueOf(userRowsUpdated)+" rows updated in User table\n");
		}
		
		for(Long id : rolePerMap.keySet()) {
			if(rolePerMap.get(id)== RoleStatus.getvalueOf("APPROVED")) {
				approveRoleRowsUpdated=	userRoleRepo.approveRole(approveUserDetailRequest.userId(),id);
				System.out.println(approveRoleRowsUpdated+" rows approved in UserRole table");
				returnString+=(String.valueOf(approveRoleRowsUpdated)+" rows approved in UserRole table\n");
			}
			else if(rolePerMap.get(id)== RoleStatus.getvalueOf("REJECTED")) {
				rejectRoleRowsUpdated=userRoleRepo.rejectRole(approveUserDetailRequest.userId(),id);
				System.out.println(rejectRoleRowsUpdated+" rows rejected in UserRole table");
				returnString+=(String.valueOf(rejectRoleRowsUpdated)+" rows rejected in UserRole table\n");

			}
			
		}
		
		return returnString;
	}
	
	@Transactional
	public void assignRoleToUser(String username, List<String> roleNameList) {
		List<RoleNames> roleNames=roleNameList.stream().map(role-> RoleNames.getvalueOf(role)).toList();
		
		User user = userRepository.findAuthUser(username, UserStatus.ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

		List<Role> rolesFromDb = roleRepository.findByNameIn(roleNames);

		Set<RoleNames> foundRoles = rolesFromDb.stream().map(Role::getName).collect(Collectors.toSet());

		List<RoleNames> missingRoles = roleNames.stream().filter(role -> !foundRoles.contains(role)).toList();

		if (!missingRoles.isEmpty()) {
			throw new RoleNotFoundException(missingRoles);
		}

		Set<RoleNames> existingRoles = user.getUserRoles().stream().map(ur -> ur.getRole().getName())
				.collect(Collectors.toSet());

		List<RoleNames> duplicateRoles = roleNames.stream().filter(existingRoles::contains).toList();

		if (!duplicateRoles.isEmpty()) {
			throw new RoleAlreadyAssignedException(duplicateRoles);
		}

		rolesFromDb.forEach(role -> {
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			if (isAdmin()) {
            	userRole.setStatus(RoleStatus.APPROVED);
			}
            else {
            	userRole.setStatus(RoleStatus.REQUESTED);
            }
			user.addUserRole(userRole); // sets both sides

		});

		userRepository.save(user);
	}


}
