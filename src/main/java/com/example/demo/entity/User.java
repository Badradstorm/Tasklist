package com.example.demo.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "usr")
@Entity
public class User {

  @Id
  @GeneratedValue
  private int id;

  @NotEmpty(message = "Вы не указали имя")
  @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов")
  private String username;

  @NotEmpty(message = "Вы не указали пароль")
  private String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Task> taskList;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return id == user.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password);
  }
}
