package com.example.taskqueue.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskqueue.TaskQueueApplication;
import com.example.taskqueue.dto.TaskDto;
import com.example.taskqueue.model.Task;
import com.example.taskqueue.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    webEnvironment = WebEnvironment.MOCK,
    classes = TaskQueueApplication.class)
@AutoConfigureMockMvc
class TaskServiceTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private TaskService taskService;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper objectMapper;


  @Test
  void getList() {
    List<Task> expected = List.of(Task.builder()
        .id(1L)
        .name("name")
        .user(null)
        .build());

    when(taskService.getList()).thenReturn(expected);

    List<Task> actual = taskService.getList();
    assertEquals(expected, actual);

  }

  @Test
  void createTask() throws Exception {
    User expectedUser = User.builder()
        .user("user")
        .password("user")
        .role("USER")
        .enabled(true)
        .build();

    Task expectedTask =Task.builder()
        .id(1L)
        .name("name")
        .user(expectedUser)
        .build();

    when(taskService.createTask(any())).thenReturn(expectedTask);

    Task actualTask = taskService.createTask(TaskDto.builder().task("a").worker("a").build());

    assertEquals(expectedTask, actualTask);
  }

  @Test
  @WithMockUser(username = "user", authorities = "PRIV_GET_TASK")
  void getFirstTask() throws Exception {
    Task expected = Task.builder()
        .id(1L)
        .name("name")
        .user(null)
        .build();

    when(taskService.getFirstTask(any())).thenReturn(expected);
    Task actual = taskService.getFirstTask(any());

    assertEquals(expected, actual);
    mockMvc.perform(get("/task/mytasks"))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void getPrivateTasks() {
  }
}