package org.aleksey.task_tracker.store.repositories;

import org.aleksey.task_tracker.store.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
