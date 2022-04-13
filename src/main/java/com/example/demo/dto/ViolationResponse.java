package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ViolationResponse extends Response {

  private final String fieldName;

  @Builder
  public ViolationResponse(String message, String fieldName) {
    super(message);
    this.fieldName = fieldName;
  }
}
