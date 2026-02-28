package com.ded.BTS.DTO.request.mapper;

import java.lang.annotation.Target;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.DTO.request.CreateProjectRequest;
import com.ded.BTS.model.Project;

@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.ERROR)
public interface CreateProjectRequestMapper {

	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "recStartDate", ignore = true)
	@Mapping(target = "recEndDate", ignore = true)
	@Mapping(target = "owner", ignore = true)
	Project toEntity(CreateProjectRequest createProjectRequest);
	
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "recStartDate", ignore = true)
    @Mapping(target = "recEndDate", ignore = true)
	@Mapping(target = "owner",ignore =true)
	void updateEntityFromDto(CreateProjectRequest createProjectRequest, @MappingTarget Project project);
}
