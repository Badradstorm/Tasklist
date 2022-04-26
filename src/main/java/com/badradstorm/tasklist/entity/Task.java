package com.badradstorm.tasklist.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Task extends BaseEntity {

  @NotBlank(message = "Вы не указали название задачи")
  private String title;

  private boolean completed;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Task)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Task task = (Task) o;
    return completed == task.completed && Objects.equals(title, task.title)
        && Objects.equals(user, task.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), title, completed, user);
  }
}
