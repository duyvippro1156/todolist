package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Task;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
	@Query(value = "SELECT t1.* FROM `task` t1 WHERE t1.list_id=?1 AND t1.name like %?2%", nativeQuery = true)
	List<Task> searchByTaskname(Long id, String keyWord);

	@Query(value = "SELECT t1.* FROM `task` t1 WHERE t1.list_id=?1 AND t1.is_delete=0", nativeQuery = true)
	List<Task> getAllTaskByTaskListId(Long listId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE task t1 SET t1.is_delete = 1 WHERE t1.id = ?1", nativeQuery = true)
	void softDelete (Long id);

	@Query(value = "SELECT t1.* FROM `task` t1 WHERE t1.target_date like %?1%", nativeQuery = true)
	Task findByTargetDate(String targetDate);

} 

