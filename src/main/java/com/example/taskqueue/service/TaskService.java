package com.example.taskqueue.service;

import com.example.taskqueue.dto.TaskDto;
import com.example.taskqueue.model.Task;
import com.example.taskqueue.model.User;
import com.example.taskqueue.repository.TaskRepository;
import com.example.taskqueue.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final UserRepository userRepository;

  private final TaskNotifyService taskNotifyService;

  public List<Task> getList() {
    return taskRepository.findAll();
  }

  @Transactional
  public Task createTask(TaskDto taskDto) {
    Optional<User> worker = userRepository.findByUser(taskDto.getWorker());
    User user = null;
    if (worker.isPresent()) {
      user = worker.get();
    }
    Task createdTask = Task.builder()
        .id(0L)
        .name(taskDto.getTask())
        .user(user)
        .build();
    taskNotifyService.notifyNewTask(createdTask);

    return taskRepository.save(createdTask);
  }

  public Task getFirstTask(User user) {
    Optional<Task> optionalTask = taskRepository.findFirstByUserOrderById(user);
    if (optionalTask.isPresent()) {
      return optionalTask.get();
    } else {
      Optional<Task> newTask = taskRepository.findFirstByUserIsNullOrderById();
      if (newTask.isPresent()) {
        newTask.get().setUser(user);
        return taskRepository.save(newTask.get());
      }
      else return null;
    }
  }

  public void deleteTaskById(Long id) {
    taskRepository.deleteById(id);
  }

  public List<Task> getPrivateTasks(User user) {
    return taskRepository.findTaskByUser(user);
  }
}
