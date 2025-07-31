package br.com.mixzyn.erp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController extends AbstractController<Produto>{

    public ProdutoController(ProdutoService service) {
        super(service);
    }
}
