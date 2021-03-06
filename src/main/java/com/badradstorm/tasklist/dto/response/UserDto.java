package com.badradstorm.tasklist.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseResponse implements UserDetails {

  private final String username;
  private final String password;
  private final boolean isActive;
  private final Set<SimpleGrantedAuthority> authorities;
  private final List<TaskDto> taskList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return isActive;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return isActive;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return isActive;
  }
}
