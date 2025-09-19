package br.com.mixzyn.erp.service;

import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.dto.ItemVendaDTO;
import br.com.mixzyn.erp.dto.VendaDTO;
import br.com.mixzyn.erp.model.ItemVenda;
import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.model.Venda;
import br.com.mixzyn.erp.repository.VendaRepository;

@Service
public class VendaService extends AbstractService<Venda> {
    private final ProdutoService produtoService;

    public VendaService(VendaRepository repository, ProdutoService produtoService) {
        super(repository);
        this.produtoService = produtoService;
    }

    public Venda create(VendaDTO vendaDTO) {
        Venda novaVenda = new Venda();

        for (ItemVendaDTO itemVendaDTO : vendaDTO.itens()) {
            Produto produto = produtoService.findById(itemVendaDTO.idProduto())
             .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemVendaDTO.quantidade());
            item.setPrecoUnitario(produto.getPrecoUnitario()); // pegar preço do produto
            novaVenda.adicionarItem(item);
        }

        return repository.save(novaVenda);
    }
}
