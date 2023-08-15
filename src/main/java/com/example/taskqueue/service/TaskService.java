package com.example.taskqueue.service;

import com.example.taskqueue.dto.TaskDto;
import com.example.taskqueue.model.Task;
import com.example.taskqueue.model.User;
import com.example.taskqueue.repository.TaskRepository;
import com.example.taskqueue.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final UserRepository userRepository;

  public List<Task> getList() {
    return taskRepository.findAll();
  }

  public Task createTask(TaskDto taskDto) {
    Optional<User> worker = userRepository.findByUser(taskDto.getWorker());
    User user = null;
    if(worker.isPresent()){
      user = worker.get();
    }

    return taskRepository.save(Task.builder()
        .id(0L)
        .name(taskDto.getTask())
        .user(user)
        .build());
  }

  public Task getFirstTask(User user){
    Optional<Task> optionalTask = taskRepository.findFirstByUserOrderById(user);
    if (optionalTask.isPresent()) {
      return optionalTask.get();
    }
    else {
      Task newTask = taskRepository.findFirstByUserIsNullOrderById().get();
      newTask.setUser(user);
      return taskRepository.save(newTask);
    }
  }

  public void deleteTaskById(Long id){
    taskRepository.deleteById(id);
  }

  public List<Task> getPrivateTasks(User user){
    return taskRepository.findTaskByUser(user);
  }
}
