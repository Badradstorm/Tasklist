package com.badradstorm.tasklist.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesRegex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//  private UserDto userDto;
//
//  @MockBean
//  private UserService service;
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @BeforeEach
//  void setUp() {
////    userDto = new UserDto("name", "", true, Collections.emptyList(), Collections.emptyList());
//    userDto.setId(1);
//  }
//
//  @Test
//  void testGetAll() throws Exception {
//    when(service.getAll()).thenReturn(List.of(userDto));
//
//    this.mockMvc
//        .perform(get("/user"))
//        .andExpect(status().isOk())
//        .andExpect(content()
//            .string(containsString("[{\"id\":1,\"username\":\"name\",\"taskList\":[]}]")))
//        .andDo(print());
//  }
//
//  @Test
//  void testGetOne() throws Exception {
//    when(service.getOne(anyInt())).thenReturn(userDto);
//
//    this.mockMvc
//        .perform(get("/user/1"))
//        .andExpect(status().isOk())
//        .andExpect(content()
//            .string(containsString("{\"id\":1,\"username\":\"name\",\"taskList\":[]}")))
//        .andDo(print());
//  }
//
//  @Test
//  void testCreate() throws Exception {
//    when(service.create(any(User.class))).thenReturn(userDto);
//
//    this.mockMvc
//        .perform(post("/user")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content("{\"username\":\"name\",\"password\":\"pass\"}"))
//        .andExpect(status().isCreated())
//        .andExpect(header().exists("Location"))
//        .andExpect(header().string("Location", containsString("http://localhost/user/1")))
//        .andDo(print());
//  }
//
//  @Test
//  void testCreateNotValidUser() throws Exception {
//    when(service.create(any(User.class))).thenReturn(userDto);
//
//    this.mockMvc
//        .perform(post("/user")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content("{\"username\":\"na\"}"))
//        .andExpect(status().isBadRequest())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//        .andExpect(content().string(matchesRegex(".*Вы не указали пароль.*")))
//        .andExpect(content().string(matchesRegex(".*Имя должно содержать не менее 3 и не более 20 символов.*")))
//        .andDo(print());
//  }
//
//  @Test
//  void testUpdate() throws Exception {
//    when(service.update(any(User.class))).thenReturn(userDto);
//
//    this.mockMvc
//        .perform(put("/user")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content("{\"username\":\"name\",\"password\":\"pass\"}"))
//        .andExpect(status().isCreated())
//        .andExpect(header().exists("Location"))
//        .andExpect(header().string("Location", containsString("http://localhost/user/1")))
//        .andDo(print());
//  }
//
//  @Test
//  void testDelete() throws Exception {
////    when(service.delete(anyInt())).thenReturn(1);
//
//    this.mockMvc
//        .perform(delete("/user/1"))
//        .andExpect(status().isOk())
//        .andExpect(content().string(containsString("1")))
//        .andDo(print());
//  }
//}