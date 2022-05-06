package com.badradstorm.tasklist.dto.mapper;

import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.Role;
import com.badradstorm.tasklist.entity.Status;
import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.entity.User;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserMapperTest {

  private final UserMapper userMapper = new UserMapperImpl(new TaskMapperImpl(), new StatusMapperImpl(), new RoleMapperImpl());

  @Test
  void toDto() {
    User user = new User();
    user.setId(1);
    user.setUsername("name");
    Task task = new Task();
    task.setTitle("title");
    user.setTaskList(List.of(task));
    user.setRole(Role.USER);
    user.setStatus(Status.ACTIVE);

    UserDto userDto = userMapper.toDto(user);

    Assertions.assertEquals(user.getId(), userDto.getId());
    Assertions.assertEquals(user.getUsername(), userDto.getUsername());
    Assertions.assertEquals(
        user.getTaskList().get(0).getTitle(),
        userDto.getTaskList().get(0).getTitle());
    Assertions.assertEquals(user.getRole().getAuthorities().size(), userDto.getAuthorities().size());
    Assertions.assertTrue(userDto.isActive());
  }
}