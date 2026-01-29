package br.com.mixzyn.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.dto.ItemVendaCreateDTO;
import br.com.mixzyn.erp.dto.ItemVendaDetailsDTO;
import br.com.mixzyn.erp.dto.VendaResumoDTO;
import br.com.mixzyn.erp.dto.VendaCreateDTO;
import br.com.mixzyn.erp.dto.VendaDetailsDTO;
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

    public Venda createVenda(VendaCreateDTO vendaDTO) {
        Venda novaVenda = new Venda();

        for (ItemVendaCreateDTO itemVendaDTO : vendaDTO.itens()) {
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

    public VendaDetailsDTO getVendaDetails(Long id) {
        Venda venda = repository.findById(id)
         .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        List<ItemVendaDetailsDTO> itensDTO = venda.getItens().stream()
         .map(item -> new ItemVendaDetailsDTO(
             item.getProduto().getId(),
             item.getProduto().getDescricao(),
             item.getQuantidade(),
             item.getPrecoUnitario(),
             item.getProduto().getImagePath()
         ))
         .toList();

        return new VendaDetailsDTO(venda.getId(), venda.getData(), venda.getValorTotal(), itensDTO);
    }

    public List<VendaResumoDTO> findAllVendas() {
        return repository.findAllResumo();
    }
}
