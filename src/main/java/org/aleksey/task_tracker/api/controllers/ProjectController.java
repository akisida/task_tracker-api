package org.aleksey.task_tracker.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aleksey.task_tracker.api.dto.AckDto;
import org.aleksey.task_tracker.api.dto.ProjectDTO;
import org.aleksey.task_tracker.api.exceptions.BadRequestException;
import org.aleksey.task_tracker.api.exceptions.NotFoundException;
import org.aleksey.task_tracker.api.factories.ProjectDtoFactory;
import org.aleksey.task_tracker.store.models.ProjectEntity;
import org.aleksey.task_tracker.store.repositories.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@Transactional
public class ProjectController {
    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;
    public static final String FETCH_PROJECT = "/api/projects";

    public static final String DELETE_PROJECT ="/api/projects/{project_id}";
    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDTO> fetchProjects(@RequestParam(value = "prefix_name",required = false) Optional<String> optionalPrefixName){
        optionalPrefixName = optionalPrefixName.filter(prefixName ->!prefixName.trim().isEmpty());
        Stream<ProjectEntity> projectEntityStream= optionalPrefixName.map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAll);
        return projectEntityStream.map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());

    }

    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDTO createOrUpdateProject(
            @RequestParam String name,
            @RequestParam(value = "project_id",required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName
        )
    {
        optionalProjectName = optionalProjectName.filter(projectName ->projectName.trim().isEmpty());
        boolean isCreate = !optionalProjectId.isPresent();
        ProjectEntity project = optionalProjectId
                .map(this::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder()
                        .build());
        if(isCreate && !optionalProjectName.isPresent()){
            throw new BadRequestException("Name can't be empty");
        }
        optionalProjectName
                .ifPresent(projectName ->{
                    projectRepository.findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                            });
                    project.setName(projectName);
                });

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);
        return projectDtoFactory.makeProjectDto(savedProject);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject (@PathVariable("project_id") Long projectId){
        ProjectEntity project = getProjectOrThrowException(projectId);
        projectRepository.deleteById(projectId);
        return AckDto.makeDefault(true);
    }

    private ProjectEntity getProjectOrThrowException(Long projectId)
    {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with \"%s\" doesn't exist.", projectId)));
    }
}
