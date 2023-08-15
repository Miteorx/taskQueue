package com.example.taskqueue.conroller;

import com.example.taskqueue.dto.TaskDto;
import com.example.taskqueue.model.Task;
import com.example.taskqueue.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final TaskService taskService;

    @SubscribeMapping("/topic/tasks")
    public List<Task> sendTasksOnConnect() {
      return taskService.getList();
    }

    @MessageMapping("/task")
    @SendTo("/topic/tasks")
    public Task sendTask(TaskDto taskDto){
      return taskService.createTask(taskDto);
    }

}
