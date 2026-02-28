package com.ded.BTS.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ded.BTS.DTO.request.CreateProjectRequest;
import com.ded.BTS.DTO.request.mapper.CreateProjectRequestMapper;
import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.Project;
import com.ded.BTS.model.ProjectSummary;
import com.ded.BTS.model.User;
import com.ded.BTS.repository.ProjectRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.security.model.CurrentUser;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService {

	private final ProjectRepo projectRepo;
	private final CreateProjectRequestMapper createProjectRequestMapper;
	private final CurrentUser currentUser;
	private final UserRepo userRepo;

	public ProjectService(ProjectRepo projectRepo, CreateProjectRequestMapper createProjectRequestMapper,
			CurrentUser currentUser, UserRepo userRepo) {
		super();
		this.projectRepo = projectRepo;
		this.createProjectRequestMapper = createProjectRequestMapper;
		this.currentUser = currentUser;
		this.userRepo = userRepo;
	}

	public Project getProjectById(Long id) {
		return projectRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Project with id " + id + " not found"));

	}

	public List<Project> getProjects() {
		return projectRepo.findAll();

	}

	@Transactional
	public Project createProject(CreateProjectRequest createProjectRequest) {

		Project project = createProjectRequestMapper.toEntity(createProjectRequest);
		String currentUserName = currentUser.getLoggedInUserId();
		project.setOwner(userRepo.findByUsername(currentUserName,UserStatus.ACTIVE).orElseThrow(
				() -> new EntityNotFoundException("User with username " + currentUserName + " not found")));
		return projectRepo.save(project);
	}
	@Transactional
	public Project updateProject(Long id, CreateProjectRequest updateProjectRequest) {
		Project project = projectRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Project with id " + id + " not found"));
		createProjectRequestMapper.updateEntityFromDto(updateProjectRequest, project);
		return projectRepo.save(project);
	}
	
	@PreAuthorize("hasAuthority(ROLE_ADMIN)")
	@Transactional
	public String deleteProject(Long id) {
		Project project=projectRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Project with id " + id + " not found"));
		project.setRecEndDate(Instant.now());
		projectRepo.save(project);
		return "Success";
	}

}
