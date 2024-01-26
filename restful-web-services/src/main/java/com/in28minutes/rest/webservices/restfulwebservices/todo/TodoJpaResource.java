package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoJpaResource {

	private TodoService todoservice;

	private TodoRepository todoRepository;

	public TodoJpaResource(TodoService todoservice, TodoRepository todoRepository) {
		this.todoservice = todoservice;
		this.todoRepository = todoRepository;
	}

	@GetMapping("/users/{username}/todos")
	public List<Todo> retrieveTodos(@PathVariable String username) {
		// return todoservice.findByUsername(username);
		return todoRepository.findByUsername(username);
	}

	@GetMapping("/users/{username}/todos/{id}")
	public Todo retrieveTodo(@PathVariable String username, @PathVariable int id) {
		// return todoservice.findById(id);
		return todoRepository.findById(id).get();
	}

	// 성공 상태를 리턴할 수 있고, void로 해서 아무것도 리턴하지 않을 수 있음
	// response 엔터티를 사용하여 콘텐츠가 없는 상태를 전송하는 방법도 있음
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable int id) {
		// todoservice.deleteById(id);
		todoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// Bean에 대한 RequestBody 매핑은 속성 이름을 기초로 이루어짐
	// 즉 속성 이름이 bean에 있는 필드 이름과 정확히 일치하도록 해야함

	// Repository에는 update create가 없어서 save로 통일
	@PutMapping("/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username, @PathVariable int id, @RequestBody Todo todo) {
		// todoservice.updateTodo(todo);
		todoRepository.save(todo);
		return todo;
	}

	// 이거때문에 원래 Todo에 id가 int였는데 Integer로 바꿔줘야함 ( int는 null값을 가질 수 없음 save는 id필드를
	// 바탕으로 update인지 create인지 알아냄 )
	@PostMapping("/users/{username}/todos")
	public Todo createTodo(@PathVariable String username, @RequestBody Todo todo) {
		todo.setUsername(username);
		todo.setId(null);
		return todoRepository.save(todo);
		// Todo createdTodo = todoservice.addTodo(username, todo.getDescription(),
		// todo.getTargetDate(), todo.isDone());
		// return createdTodo;
	}
}
