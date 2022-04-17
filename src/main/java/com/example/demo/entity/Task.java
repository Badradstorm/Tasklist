package com.example.demo.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Task {

  @Id
  @GeneratedValue
  private int id;
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
    Task todo = (Task) o;
    return Objects.equals(title, todo.title)
        && Objects.equals(user, todo.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, completed, user);
  }
}
