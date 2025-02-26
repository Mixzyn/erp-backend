package br.com.mixzyn.erp.service;

import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.repository.ProdutoRepository;

@Service
public class ProdutoService extends AbstractService<Produto> {
    
    public ProdutoService(ProdutoRepository repository) {
        super(repository);
    }
}
