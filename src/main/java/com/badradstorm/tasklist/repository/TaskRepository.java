package com.badradstorm.tasklist.repository;

import com.badradstorm.tasklist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
