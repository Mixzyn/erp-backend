package br.com.mixzyn.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IController<T> {
    ResponseEntity<List<T>> getAll();

    ResponseEntity<?> get(Long id);

    ResponseEntity<?> delete(Long id);
}
