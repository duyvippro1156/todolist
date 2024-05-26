package com.example.demo.repository;

import com.example.demo.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findTaskListByBoard_Id(Long id);

    @Query(value = "SELECT l1.* FROM `lists` t1 WHERE l1.board_id=?1 AND l1.name like %?2%", nativeQuery = true)
    List<TaskList> searchByListName(Long id, String keyWord);
}
