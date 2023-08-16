package com.example.taskqueue.conroller;

import com.example.taskqueue.dto.TaskDto;
import com.example.taskqueue.model.Task;
import com.example.taskqueue.model.User;
import com.example.taskqueue.service.TaskService;
import com.example.taskqueue.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public String getCreateTask(Model model) {
    List<User> workers = userService.getAllWorker();
    model.addAttribute("workers", workers);
    return "task";
  }

  @PostMapping("/create")
  public String postCreateTask(
      @RequestParam String task,
      @RequestParam String worker) {

    taskService.createTask(TaskDto.builder()
        .task(task)
        .worker(worker)
        .build());

    return "redirect:/";
  }

  @GetMapping("/mytasks")
  public String mytasks(Model model, @AuthenticationPrincipal UserDetails userDetails) {

    String loggedUser = userDetails.getUsername();

    User user = userService.getUserByUsername(loggedUser);

    List<Task> taskList = taskService.getPrivateTasks(user);

    Task task = taskService.getFirstTask(user);

    model.addAttribute("firstTask", task);

    model.addAttribute("taskList", taskList);
    return "mytasks";
  }

  @DeleteMapping("/mytasks/delete/{id}")
  public String deleteTask(@PathVariable("id") String id) {
    taskService.deleteTaskById(Long.valueOf(id));
    return "redirect:/task/mytasks";
  }

}
