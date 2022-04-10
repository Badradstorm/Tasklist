package com.example.demo.dto;

import lombok.Data;

@Data
public class TodoDto {

  private int id;
  private String title;
  private boolean completed;
}
