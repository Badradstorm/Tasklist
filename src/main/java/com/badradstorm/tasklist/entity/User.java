package com.badradstorm.tasklist.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usr")
@Entity
public class User extends BaseEntity {

  @NotEmpty(message = "Вы не указали имя")
  @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов")
  private String username;

  @NotEmpty(message = "Вы не указали пароль")
  private String password;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
  private List<Task> taskList;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(username, user.username) && Objects.equals(password,
        user.password) && Objects.equals(taskList, user.taskList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), username, password, taskList);
  }
}
