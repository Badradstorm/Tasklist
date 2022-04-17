package com.example.demo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.TaskDto;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

  private TaskDto taskDto;

  @MockBean
  private TaskService service;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    taskDto = new TaskDto();
    taskDto.setId(1);
    taskDto.setTitle("title");
    taskDto.setCompleted(false);
  }

  @Test
  void testCreate() throws Exception {
    when(service.create(any(Task.class), anyInt())).thenReturn(taskDto);

    this.mockMvc
        .perform(post("/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"title\",\"completed\": false}")
            .queryParam("userId", "2"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", containsString("http://localhost/task/1")))
        .andDo(print());
  }

  @Test
  void testCreateNotValidTask() throws Exception {
    when(service.create(any(Task.class), anyInt())).thenReturn(taskDto);

    this.mockMvc
        .perform(post("/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"\"}")
            .queryParam("userId", "2"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(containsString("{\"violations\":"
            + "[{\"message\":\"title\",\"fieldName\":\"Вы не указали название задачи\"}]}")))
        .andDo(print());
  }

  @Test
  void testUpdate() throws Exception {
    when(service.update(any(Task.class), anyInt())).thenReturn(taskDto);

    this.mockMvc
        .perform(put("/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"title\",\"completed\": false}")
            .queryParam("userId", "2"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", containsString("http://localhost/task/1")))
        .andDo(print());
  }

  @Test
  void testUpdateNotValidTask() throws Exception {
    when(service.update(any(Task.class), anyInt())).thenReturn(taskDto);

    this.mockMvc
        .perform(put("/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\",\"title\": \"\"}")
            .queryParam("userId", "2"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(containsString("{\"violations\":"
            + "[{\"message\":\"title\",\"fieldName\":\"Вы не указали название задачи\"}]}")))
        .andDo(print());
  }

  @Test
  void testDelete() throws Exception {
    when(service.delete(anyInt())).thenReturn(2);

    this.mockMvc
        .perform(delete("/task")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParam("taskId", "2"))
        .andExpect(status().isOk())
        .andExpect(content().string("2"))
        .andDo(print());
  }
}