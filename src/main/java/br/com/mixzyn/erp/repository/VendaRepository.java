package br.com.mixzyn.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mixzyn.erp.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
