package com.example.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDto extends BaseDto {

  private String title;
  private boolean completed;
}
