package br.com.mixzyn.erp.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    Optional<T> findById(Long id);

    List<T> findAll();

    boolean update(T obj);

    boolean delete(Long id);
}
