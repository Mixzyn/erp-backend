package br.com.mixzyn.erp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mixzyn.erp.dto.VendaCreateDTO;
import br.com.mixzyn.erp.dto.VendaDetailsDTO;
import br.com.mixzyn.erp.dto.VendaResumoDTO;
import br.com.mixzyn.erp.model.Venda;
import br.com.mixzyn.erp.service.VendaService;

@Controller
@RequestMapping("/vendas")
public class VendaController extends AbstractController<Venda> {
    private final VendaService service;

    public VendaController(VendaService service) {
        super(service);
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Venda> post(@RequestBody VendaCreateDTO vendaDTO) {
        Venda novaVenda = service.createVenda(vendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    @GetMapping
    public ResponseEntity<List<VendaResumoDTO>> getAllVendas() {
        return ResponseEntity.ok(service.findAllVendas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDetailsDTO> getVendaDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getVendaDetails(id));
    }
}
