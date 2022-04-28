package com.badradstorm.tasklist.dto.response;

import com.badradstorm.tasklist.dto.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDto extends BaseResponse {

  private String title;
  private boolean completed;
}
