package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ValidationErrorResponse {

  private List<ViolationResponse> violations = new ArrayList<>();
}
