package com.example.demo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  private UserDto userDto;

  @MockBean
  private UserService service;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    userDto = new UserDto();
    userDto.setId(1);
    userDto.setUsername("name");
    userDto.setTaskList(Collections.emptyList());
  }

  @Test
  void testGetAll() throws Exception {
    when(service.getAll()).thenReturn(List.of(userDto));

    this.mockMvc
        .perform(get("/user"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("[{\"id\":1,\"username\":\"name\",\"taskList\":[]}]")))
        .andDo(print());
  }

  @Test
  void testGetOne() throws Exception {
    when(service.getOne(anyInt())).thenReturn(userDto);

    this.mockMvc
        .perform(get("/user/1"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("{\"id\":1,\"username\":\"name\",\"taskList\":[]}")))
        .andDo(print());
  }

  @Test
  void testCreate() throws Exception {
    doNothing().when(service).create(any(User.class));

    this.mockMvc
        .perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":1,\"username\":\"name\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Пользователь успешно сохранен")))
        .andDo(print());
  }

  @Test
  void testDelete() throws Exception {
    when(service.delete(anyInt())).thenReturn(1);

    this.mockMvc
        .perform(delete("/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("1")))
        .andDo(print());
  }
}