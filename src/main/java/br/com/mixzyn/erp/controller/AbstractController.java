package br.com.mixzyn.erp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.mixzyn.erp.service.AbstractService;

public class AbstractController<T> implements IController<T> {
    protected final AbstractService<T> service;

    public AbstractController (AbstractService<T> service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
