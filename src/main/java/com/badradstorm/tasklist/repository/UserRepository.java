package com.badradstorm.tasklist.repository;

import com.badradstorm.tasklist.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);
}
