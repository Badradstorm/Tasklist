package com.badradstorm.tasklist.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDto extends BaseResponse {

  private String title;
  private boolean completed;
}
