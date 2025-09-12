package br.com.mixzyn.erp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.mixzyn.erp.service.AbstractService;

public class AbstractController<T> implements IController<T> {

    protected AbstractService<T> service;

    public AbstractController (AbstractService<T> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<T> post(@RequestBody T entity) {
        return ResponseEntity.ok(service.create(entity));
    }

    @PutMapping
    public ResponseEntity<T> put(@RequestBody T entity) {
        return ResponseEntity.ok(service.create(entity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<T>> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
