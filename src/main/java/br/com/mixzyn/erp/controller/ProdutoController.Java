package br.com.mixzyn.erp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mixzyn.erp.dto.ProdutoDto;
import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // Upload de produto com imagem
    @PostMapping
    public ResponseEntity<Produto> post(@ModelAttribute ProdutoDto produto) {
        Produto novoProduto;
        try {
            novoProduto = service.create(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Boolean> put(@RequestBody Produto produto) {
        return ResponseEntity.ok(service.update(produto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Produto>> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
