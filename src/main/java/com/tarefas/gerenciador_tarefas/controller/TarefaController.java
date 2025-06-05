package com.tarefas.gerenciador_tarefas.controller;

import com.tarefas.gerenciador_tarefas.dto.TarefaDTO;
import com.tarefas.gerenciador_tarefas.model.Tarefa;
import com.tarefas.gerenciador_tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody TarefaDTO dto) {
        Tarefa tarefa = new Tarefa(dto.getTitulo(), dto.getDescricao(), dto.getStatus());
        return ResponseEntity.ok(tarefaService.salvar(tarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody TarefaDTO dto) {
        Tarefa novaTarefa = new Tarefa(dto.getTitulo(), dto.getDescricao(), dto.getStatus());
        try {
            Tarefa atualizada = tarefaService.atualizar(id, novaTarefa);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
