package com.example.demo.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lists")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private long id; 

	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "taskList", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	Set<Task> tasks;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "board_id", referencedColumnName = "id")
	Board board;
} 

