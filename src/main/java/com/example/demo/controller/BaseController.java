package com.example.demo.controller;

import com.example.demo.dto.BaseDto;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class BaseController {

  protected ResponseEntity<?> responseWithLocation(BaseDto baseDto) {
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(baseDto.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }
}
