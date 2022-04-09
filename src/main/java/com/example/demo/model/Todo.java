package com.example.demo.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Todo {

  @Id
  @GeneratedValue
  private int id;
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
    if (!(o instanceof Todo)) {
      return false;
    }
    Todo todo = (Todo) o;
    return id == todo.id && completed == todo.completed && Objects.equals(title, todo.title)
        && Objects.equals(user, todo.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, completed, user);
  }
}
