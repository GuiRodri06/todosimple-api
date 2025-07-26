package com.lucasangelo.todosimple.controllers;

import com.lucasangelo.todosimple.models.Task;
import com.lucasangelo.todosimple.service.TaskService;
import com.lucasangelo.todosimple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/user/{userId}") // Mapeia requisições GET para o endpoint /task/user/{userId}
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId) {
        // @PathVariable: extrai o valor do parâmetro 'userId' diretamente da URL

        userService.findById(userId);

        List<Task> objs = this.taskService.findAllByUserID(userId);
        // Chama o serviço (taskService) para buscar todas as tarefas relacionadas ao usuário com o ID fornecido

        return ResponseEntity.ok().body(objs);
        // Cria uma resposta HTTP 200 (OK) com a lista de tarefas (objs) no corpo da resposta
    }


    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj) {
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();

    }



}
