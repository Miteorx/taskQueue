package com.example.taskqueue.repository;

import com.example.taskqueue.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
