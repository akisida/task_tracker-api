package org.aleksey.task_tracker.store.repositories;

import org.aleksey.task_tracker.store.models.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
    Optional<ProjectEntity> findByName(String name);
    Stream<ProjectEntity> streamAll();
    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);

}
