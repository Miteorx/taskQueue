package com.example.taskqueue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taskqueue.TaskQueueApplication;
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
class UserServiceTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(username="user", authorities = "PRIV_GET_TASK")
  void getAccessToAdminPageWithUserRole() throws Exception {
    mockMvc.perform(get("/task/create"))
        .andExpect(status().isForbidden())
        .andReturn();
  }

  @Test
  @WithMockUser(username="user", authorities = "PRIV_CREATE_TASK")
  void getAccessToWorkerPanelAsAdmin() throws Exception {
    mockMvc.perform(get("/task/mytasks"))
        .andExpect(status().isForbidden())
        .andReturn();
  }

  @Test
  void getUserByUsername() throws Exception {
    User expectedUser = User.builder()
        .user("user")
        .password("user")
        .role("USER")
        .enabled(true)
        .build();
    User expectedAdmin = User.builder()
        .user("admin")
        .password("admin")
        .role("ADMIN")
        .enabled(true)
        .build();

    when(userService.getUserByUsername("user")).thenReturn(expectedUser);

    when(userService.getUserByUsername("admin")).thenReturn(expectedAdmin);


    User actualUser = userService.getUserByUsername("user");
    User actualAdmin = userService.getUserByUsername("admin");
    assertEquals(expectedUser, actualUser);
    assertEquals(expectedAdmin, actualAdmin);
  }

  @Test
  @WithMockUser(username="admin", authorities = "PRIV_CREATE_TASK")
  void getAllWorker() throws Exception {
    List<User> expected = List.of(
        User.builder()
            .user("user")
            .password("user")
            .role("USER")
            .enabled(true)
            .build(),

        User.builder()
            .user("user2")
            .password("user2")
            .role("USER")
            .enabled(true)
            .build()
    );

    when(userService.getAllWorker()).thenReturn(expected);
    List<User> actual = userService.getAllWorker();
    assertEquals(expected, actual);

    mockMvc.perform(get("/task/create"))
        .andExpect(status().isOk())
        .andReturn();
  }
}