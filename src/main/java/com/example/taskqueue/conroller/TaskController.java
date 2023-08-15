package com.example.taskqueue.conroller;

import com.example.taskqueue.dto.TaskDto;
import com.example.taskqueue.model.Task;
import com.example.taskqueue.model.User;
import com.example.taskqueue.service.TaskService;
import com.example.taskqueue.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

  private final UserService userService;

  private final TaskService taskService;

  @GetMapping("/create")
  public String getCreateTask(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    List<User> workers = userService.getAllWorker();
    model.addAttribute("workers", workers);
    return "task";
  }


  @PostMapping("/create")
  public String postCreateTask(
      @RequestParam String task,
      @RequestParam String worker,

      Model model, @AuthenticationPrincipal UserDetails userDetails) {

    taskService.createTask(TaskDto.builder()
        .task(task)
        .worker(worker)
        .build());

    return "redirect:/";
  }
}
