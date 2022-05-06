package com.badradstorm.tasklist.dto.mapper;

import static com.badradstorm.tasklist.entity.Status.ACTIVE;
import static com.badradstorm.tasklist.entity.Status.BANNED;

import com.badradstorm.tasklist.entity.Status;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StatusMapper {

  default Boolean map(Status status) {
    switch (status) {
      case ACTIVE:
        return true;
      case BANNED:
      default:
        return false;
    }
  }

  default Status map(Boolean bool) {
    if (bool) {
      return ACTIVE;
    }
    return BANNED;
  }
}
