package br.com.mixzyn.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.dto.ItemVendaDTO;
import br.com.mixzyn.erp.dto.VendaResumoDTO;
import br.com.mixzyn.erp.dto.VendaCreateDTO;
import br.com.mixzyn.erp.model.ItemVenda;
import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.model.Venda;
import br.com.mixzyn.erp.repository.VendaRepository;

@Service
public class VendaService extends AbstractService<Venda> {
    private final VendaRepository repository;
    private final ProdutoService produtoService;

    public VendaService(VendaRepository repository, ProdutoService produtoService) {
        super(repository);
        this.produtoService = produtoService;
        this.repository = repository;
    }

    public Venda create(VendaCreateDTO vendaDTO) {
        Venda novaVenda = new Venda();

        for (ItemVendaDTO itemVendaDTO : vendaDTO.itens()) {
            Produto produto = produtoService.findById(itemVendaDTO.idProduto())
             .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemVendaDTO.quantidade());
            item.setPrecoUnitario(produto.getPrecoUnitario());
            novaVenda.adicionarItem(item);
        }

        return repository.save(novaVenda);
    }

    public List<VendaResumoDTO> findAllVendas() {
        return repository.findAllResumo();
    }
}
