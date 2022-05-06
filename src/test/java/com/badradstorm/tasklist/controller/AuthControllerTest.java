package com.badradstorm.tasklist.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.badradstorm.tasklist.dto.request.AuthRequest;
import com.badradstorm.tasklist.dto.request.SignupRequest;
import com.badradstorm.tasklist.dto.response.AuthResponse;
import com.badradstorm.tasklist.dto.response.MessageResponse;
import com.badradstorm.tasklist.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class AuthControllerTest {

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private AuthService service;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();
  }

  @Test
  void testLogin() throws Exception {
    when(service.login(any(AuthRequest.class))).thenReturn(new AuthResponse("name", "token"));

    this.mockMvc
        .perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"name\",\"password\":\"pass\"}"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("{\"username\":\"name\",\"token\":\"token\"}")))
        .andDo(print());
  }

  @Test
  void testRegister() throws Exception {
    when(service.register(any(SignupRequest.class))).thenReturn(
        new MessageResponse("Пользователь успешно зарегистрирован!"));

    this.mockMvc
        .perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"name\",\"password\":\"password\"}"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("Пользователь успешно зарегистрирован!")))
        .andDo(print());
  }

  @Test
  void testUpdate() throws Exception {
    when(service.update(any(SignupRequest.class))).thenReturn(
        new MessageResponse("Имя пользователя и пароль успешно изменены!"));

    this.mockMvc
        .perform(put("/api/v1/auth/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"name\",\"password\":\"password\"}"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("Имя пользователя и пароль успешно изменены!")))
        .andDo(print());
  }
}