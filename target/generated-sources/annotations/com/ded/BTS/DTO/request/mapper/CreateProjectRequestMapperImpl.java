package com.ded.BTS.DTO.request.mapper;

import com.ded.BTS.DTO.request.CreateProjectRequest;
import com.ded.BTS.model.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T23:49:51+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CreateProjectRequestMapperImpl implements CreateProjectRequestMapper {

    @Override
    public Project toEntity(CreateProjectRequest createProjectRequest) {
        if ( createProjectRequest == null ) {
            return null;
        }

        Project project = new Project();

        project.setProjectKey( createProjectRequest.projectKey() );
        project.setName( createProjectRequest.name() );
        project.setDescription( createProjectRequest.description() );

        return project;
    }

    @Override
    public void updateEntityFromDto(CreateProjectRequest createProjectRequest, Project project) {
        if ( createProjectRequest == null ) {
            return;
        }

        if ( createProjectRequest.projectKey() != null ) {
            project.setProjectKey( createProjectRequest.projectKey() );
        }
        if ( createProjectRequest.name() != null ) {
            project.setName( createProjectRequest.name() );
        }
        if ( createProjectRequest.description() != null ) {
            project.setDescription( createProjectRequest.description() );
        }

        project.setUpdatedAt( java.time.Instant.now() );
    }
}
