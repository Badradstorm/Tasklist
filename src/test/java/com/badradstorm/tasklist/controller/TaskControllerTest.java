package com.badradstorm.tasklist.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.badradstorm.tasklist.dto.response.TaskDto;
import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
class TaskControllerTest {

  @Autowired
  private WebApplicationContext context;

  private TaskDto taskDto;

  @MockBean
  private TaskService service;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

    taskDto = new TaskDto();
    taskDto.setId(1);
    taskDto.setTitle("title");
    taskDto.setCompleted(false);
  }

  @Test
  @WithMockUser(authorities = { "tasks:write" })
  void testCreate() throws Exception {
    when(service.create(any(Task.class))).thenReturn(taskDto);

    this.mockMvc
        .perform(post("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"title\",\"completed\": false}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", containsString("http://localhost/api/v1/task/1")))
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:write" })
  void testCreateNotValidTask() throws Exception {
    when(service.create(any(Task.class))).thenReturn(taskDto);

    this.mockMvc
        .perform(post("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(containsString("{\"violations\":"
            + "[{\"message\":\"title\",\"fieldName\":\"Вы не указали название задачи\"}]}")))
        .andDo(print());
  }

  @Test
  void testCreateNotAuthorized() throws Exception {
    when(service.create(any(Task.class))).thenReturn(taskDto);

    this.mockMvc
        .perform(post("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"title\",\"completed\": false}"))
        .andExpect(status().isForbidden())
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:write" })
  void testUpdate() throws Exception {
    when(service.update(any(Task.class))).thenReturn(taskDto);

    this.mockMvc
        .perform(put("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"title\",\"completed\": false}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", containsString("http://localhost/api/v1/task/1")))
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:write" })
  void testUpdateNotValidTask() throws Exception {
    when(service.update(any(Task.class))).thenReturn(taskDto);

    this.mockMvc
        .perform(put("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(containsString("{\"violations\":"
            + "[{\"message\":\"title\",\"fieldName\":\"Вы не указали название задачи\"}]}")))
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:read" })
  void testUpdateNotAuthorized() throws Exception {
    when(service.update(any(Task.class))).thenReturn(taskDto);

    this.mockMvc
        .perform(put("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"title\",\"completed\": false}"))
        .andExpect(status().isForbidden())
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:write" })
  void testDelete() throws Exception {
    when(service.delete(anyInt())).thenReturn(2);

    this.mockMvc
        .perform(delete("/api/v1/task")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParam("taskId", "2"))
        .andExpect(status().isOk())
        .andExpect(content().string("Задача с id 2 успешно удалена!"))
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:read" })
  void testGetAll() throws Exception {
    when(service.getAll()).thenReturn(List.of(taskDto));

    this.mockMvc
        .perform(get("/api/v1/task"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content()
            .string(containsString("{\"id\":1,\"title\":\"title\",\"completed\":false}")))
        .andDo(print());
  }

  @Test
  void testGetAllNotAuthorized() throws Exception {
    when(service.getAll()).thenReturn(List.of(taskDto));

    this.mockMvc
        .perform(get("/api/v1/task"))
        .andExpect(status().isForbidden())
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = { "tasks:read" })
  void testGetOne() throws Exception {
    when(service.getOne(anyInt())).thenReturn(taskDto);

    this.mockMvc
        .perform(get("/api/v1/task/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content()
            .string(containsString("{\"id\":1,\"title\":\"title\",\"completed\":false}")))
        .andDo(print());
  }

  @Test
  void testGetOneNotAuthorized() throws Exception {
    when(service.getOne(anyInt())).thenReturn(taskDto);

    this.mockMvc
        .perform(get("/api/v1/task/1"))
        .andExpect(status().isForbidden())
        .andDo(print());
  }
}