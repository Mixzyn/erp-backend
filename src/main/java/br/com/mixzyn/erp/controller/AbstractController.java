package br.com.mixzyn.erp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.mixzyn.erp.service.AbstractService;

public class AbstractController<T> implements IController<T> {
    protected final AbstractService<T> service;

    public AbstractController (AbstractService<T> service) {
        this.service = service;
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
