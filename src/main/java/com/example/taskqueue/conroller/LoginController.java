package com.example.taskqueue.conroller;

import com.example.taskqueue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

}
