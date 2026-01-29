package br.com.mixzyn.erp.controller;

import org.springframework.http.ResponseEntity;

public interface IController<T> {
    ResponseEntity<?> delete(Long id);
}
