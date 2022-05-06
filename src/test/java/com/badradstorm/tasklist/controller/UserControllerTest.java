package com.badradstorm.tasklist.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.badradstorm.tasklist.dto.response.MessageResponse;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.service.UserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
class UserControllerTest {

  @Autowired
  private WebApplicationContext context;

  private UserDto userDto;

  @MockBean
  private UserService service;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

    userDto = new UserDto("name", "", true, Collections.emptySet(), Collections.emptyList());
    userDto.setId(1);
  }

  @Test
  @WithMockUser(authorities = {"users:read"})
  void testGetAll() throws Exception {
    when(service.getAll()).thenReturn(List.of(userDto));

    this.mockMvc
        .perform(get("/api/v1/user"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("[{\"id\":1,\"username\":\"name\",\"password\":\"\","
                + "\"authorities\":[],\"taskList\":[],\"active\":true}]")))
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = {"users:read"})
  void testGetOne() throws Exception {
    when(service.getOne(anyInt())).thenReturn(userDto);

    this.mockMvc
        .perform(get("/api/v1/user/1"))
        .andExpect(status().isOk())
        .andExpect(content()
            .string(containsString("{\"id\":1,\"username\":\"name\",\"password\":\"\","
                + "\"authorities\":[],\"taskList\":[],\"active\":true}")))
        .andDo(print());
  }

  @Test
  @WithMockUser(authorities = {"users:delete"})
  void testDelete() throws Exception {
    when(service.delete(1)).thenReturn(
        new MessageResponse(String.format("Пользователь с id %s успешно удален!", 1)));

    this.mockMvc
        .perform(delete("/api/v1/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Пользователь с id 1 успешно удален!")))
        .andDo(print());
  }
}