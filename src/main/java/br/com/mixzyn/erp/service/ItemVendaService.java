package br.com.mixzyn.erp.service;

import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.model.ItemVenda;
import br.com.mixzyn.erp.repository.ItemVendaRepository;

@Service
public class ItemVendaService extends AbstractService<ItemVenda> {
    public ItemVendaService(ItemVendaRepository repository) {
        super(repository);
    }
}
