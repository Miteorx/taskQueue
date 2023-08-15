package com.example.taskqueue.conroller;

import com.example.taskqueue.model.Task;
import com.example.taskqueue.service.TaskService;
import com.example.taskqueue.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final UserService userService;

  private final TaskService taskService;


  @GetMapping("/")
  public String main(Model model) {
    List<Task> taskList = taskService.getList();
    model.addAttribute("taskList", taskList);
    return "index";
  }


  @MessageMapping("/task.sendTask")
  @SendTo("/topic/public")
  public List<Task> sendTask(@Payload List<Task> listTask){
    return listTask;
  }

}
