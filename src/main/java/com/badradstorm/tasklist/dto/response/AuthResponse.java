package com.badradstorm.tasklist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

  private String username;
  private String token;
}
