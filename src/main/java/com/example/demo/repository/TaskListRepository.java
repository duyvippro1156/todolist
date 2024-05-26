package com.example.demo.repository;

import com.example.demo.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    TaskList findTaskListByBoard_Id(Long id);
}
