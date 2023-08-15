package com.example.taskqueue.conroller;

import com.example.taskqueue.model.Task;
import com.example.taskqueue.service.TaskService;
import com.example.taskqueue.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
  private final UserService userService;

  private final TaskService taskService;

  @GetMapping("/")
  public String main(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    List<Task> taskList = taskService.getList();
    model.addAttribute("taskList", taskList);
    return "index";
  }

}
