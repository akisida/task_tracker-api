package org.aleksey.task_tracker.store.repositories;

import org.aleksey.task_tracker.store.models.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskState extends JpaRepository<TaskStateEntity, Long> {
}
