package br.com.mixzyn.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mixzyn.erp.dto.VendaResumoDTO;
import br.com.mixzyn.erp.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("""
               select new br.com.mixzyn.erp.dto.VendaResumoDTO(v.id, v.data, v.valorTotal)
               from Venda v
            """)
    List<VendaResumoDTO> findAllResumo();
}
