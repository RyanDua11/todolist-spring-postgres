package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> listarTarefas() {
        return todoRepository.findAll();
    }
    @PostMapping
    public Todo criarTarefa(@RequestBody Todo novaTarefa) {
        return todoRepository.save(novaTarefa);
    }
    @PutMapping("/{id}")
    public Todo atualizarTarefa(@PathVariable Long id, @RequestBody Todo tarefaAtualizada) {
        Todo tarefa = todoRepository.findById(id).orElseThrow();
        tarefa.setTitulo(tarefaAtualizada.getTitulo());
        tarefa.setConcluida(tarefaAtualizada.isConcluida());
        return todoRepository.save(tarefa);
    }
    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}