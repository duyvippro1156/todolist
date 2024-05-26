package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Task;
import org.springframework.stereotype.Service;

import  com.example.demo.dto.TasksDto;

@Service
public interface TasksService {
	public Task createNewTask(TasksDto tasksDto, Long boardId, Long TaskListId);
	
	public List<Task> findTaskByListId(Long id);

	public void deleteTask(Long boardId, Long TaskListId, Long id);
	
	public Task updateTask(TasksDto tasksDto, Long boardId, Long taskListId, Long id);

	public boolean checkAuthor (Long id);

	public List<Task> searchByTaskName(Long boardId, Long taskListId, String keyWord);
}
