package com.example.taskqueue.service;

import com.example.taskqueue.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskNotifyService {

  private final SimpMessagingTemplate messagingTemplate;

  public void notifyNewTask(Task task) {
    String destination = "/topic/tasks";
    messagingTemplate.convertAndSend(destination, task);
  }
}
