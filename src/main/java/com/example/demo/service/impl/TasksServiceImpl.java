package com.example.demo.service.impl;

import com.example.demo.dto.TasksDto;
import com.example.demo.model.Task;
import com.example.demo.model.TaskList;
import com.example.demo.model.Users;
import com.example.demo.repository.TaskListRepository;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TasksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksServiceImpl implements TasksService { 

	@Autowired
	private TasksRepository taskRepository;

	@Autowired
	private TaskListRepository taskListRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Task createNewTask(TasksDto tasksDto, Long boardId, Long taskListId) {
		TaskList taskList = taskListRepository.findById(taskListId).orElseThrow();
		Task tasks = new Task();
		tasks.setName(tasksDto.getName());
		tasks.setCompleted(tasksDto.getCompleted());
		tasks.setLevel(tasksDto.getLevel());
		tasks.setStatus(tasksDto.getStatus());
		tasks.setDescription(tasksDto.getDescription());
		tasks.setFile("test");
		tasks.setTargetDate(tasksDto.getTargetDate());
		tasks.setIs_delete(0);
		tasks.setTaskList(taskList);
		if (checkAuthor(boardId)){
			return taskRepository.save(tasks);
		}else {
			return taskRepository.findById(taskListId).orElseThrow(() ->
					new UsernameNotFoundException("not found "));
		}
	}

	@Override
	public List<Task> findTaskByListId(Long id) {
		return taskRepository.getAllTaskByTaskListId(id);
	}
	
	@Override
	public void deleteTask(Long boardId, Long TaskListId, Long id) {
		if(checkAuthor(boardId)) {
			taskRepository.softDelete(id); 
		} 
		 
	} 
	
	@Override
	public Task updateTask(TasksDto tasksDto, Long boardId, Long taskListId, Long id) {
		TaskList taskList = taskListRepository.findById(taskListId).orElseThrow();
		Task tasks = taskRepository.findById(id).orElseThrow();
		tasks.setName(tasksDto.getName());
		tasks.setCompleted(tasksDto.getCompleted());
		tasks.setLevel(tasksDto.getLevel());
		tasks.setStatus(tasksDto.getStatus());
		tasks.setDescription(tasksDto.getDescription());
		tasks.setFile("test");
		tasks.setTargetDate(tasksDto.getTargetDate());
		tasks.setIs_delete(0);
		tasks.setTaskList(taskList);
		tasks.setId(id);
		if(checkAuthor(boardId)){
			return taskRepository.save(tasks);
		}else {
			return taskRepository.findById(id).orElseThrow(() ->
					new UsernameNotFoundException("not found  "));
		}
	}

	@Override
	public boolean checkAuthor(Long boardId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users author = userRepository.findAuthorByBoardId(boardId);
		Users guess = userRepository.findGuessByBoardId(boardId);
		Users userAuth = userRepository.findByEmail(auth.getName())
				.orElseThrow(() ->
						new UsernameNotFoundException("not found  "+ auth.getName()));
		if(author.getId() == userAuth.getId() || guess.getId() == userAuth.getId()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Task> searchByTaskName(Long boardId, Long taskListId, String keyWord) {
		return taskRepository.searchByTaskname(taskListId, keyWord);
	} 

} 
