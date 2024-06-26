package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.demo.model.TaskList;
import com.example.demo.repository.TasksRepository;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Component
public class WebSocketController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private TasksRepository tasksRepository;

    @Scheduled(fixedRate = 1000)
    @MessageMapping("/tasks")
    public void SendNotify() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        Task tasks = tasksRepository.findByTargetDate(formattedDateTime);
        if (tasks != null) {
            String message = tasks.getName() + " has time out!";
            messagingTemplate.convertAndSend("/topic/tasks", message);
        }
    }
}
