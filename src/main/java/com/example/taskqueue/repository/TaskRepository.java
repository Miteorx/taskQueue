package com.example.taskqueue.repository;

import com.example.taskqueue.model.Task;
import com.example.taskqueue.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findTaskByUser(User user);

  Optional<Task> findFirstByUserOrderById(User user);

  Optional<Task> findFirstByUserIsNullOrderById();

}
