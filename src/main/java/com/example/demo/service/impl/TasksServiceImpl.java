package com.example.demo.service.impl;

import com.example.demo.dto.TasksDto;
import com.example.demo.model.Board;
import com.example.demo.model.Task;
import com.example.demo.model.TaskList;
import com.example.demo.model.Users;
import com.example.demo.repository.BoardRepository;
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

	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public Task createNewTask(TasksDto tasksDto, Long boardId, Long taskListId) {
		Board board = boardRepository.findById(boardId).orElseThrow(() ->
				new UsernameNotFoundException("not found  "));
		Long board_id = board.getId();
		TaskList taskList = taskListRepository.findTaskListByBoard_Id(board_id);
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
		if(board.getTasksList().equals(taskList) && checkAuthor(boardId)){
			return taskRepository.save(tasks);
		}
		return tasks;
	}
	
	@Override
	public List<Task> getAllTask() {
		return taskRepository.findAllWithoutDeleted(); 
	} 
	
	@Override
	public Task findTaskByListId(Long id) {
		return taskRepository.findTaskByTaskList_Id(id);
	}
	
	@Override
	public void deleteTask(Long boardId, Long TaskListId, Long id) {
		if(checkAuthor(boardId)) {
			taskRepository.softDelete(id); 
		} 
		 
	} 
	
	@Override
	public Task updateTask(TasksDto tasksDto, Long boardId, Long taskListId, Long id) {
		Board board = boardRepository.findById(boardId).orElseThrow(() ->
				new UsernameNotFoundException("not found  "));
		Long board_id = board.getId();
		TaskList taskList = taskListRepository.findTaskListByBoard_Id(board_id);
		Task tasks = taskRepository.findTaskByTaskList_Id(taskListId);
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
		if(board.getTasksList().equals(taskList) && checkAuthor(boardId)){
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
		if(author.getId() == userAuth.getId() && guess.getId() == userAuth.getId()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Task> searchByTaskName(String keyWord) {
		return taskRepository.searchByTaskname(keyWord);
	} 

	
	
} 
