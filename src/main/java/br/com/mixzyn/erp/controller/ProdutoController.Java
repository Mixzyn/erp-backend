package br.com.mixzyn.erp.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mixzyn.erp.dto.ProdutoDTO;
import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController extends AbstractController<Produto> {
    private ProdutoService service;

    public ProdutoController(ProdutoService service) {
        super(service);
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Produto> postJson(@RequestBody Produto produto) {
        Produto novoProduto;
        try {
            novoProduto = service.create(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Produto> postFormData(@ModelAttribute ProdutoDTO produto) {
        Produto novoProduto;
        try {
            novoProduto = service.createWithImage(produto);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
