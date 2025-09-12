package br.com.mixzyn.erp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.dto.ProdutoDto;
import br.com.mixzyn.erp.model.AbstractEntity;
import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Value("${app.imgDir}")
    private String imgDir;

    private ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto create(ProdutoDto produto) throws IllegalStateException, IOException {
        Produto novoProduto = new Produto();

        if (produto.file() != null) {
            // Cria diretório se não existir
            File diretorio = new File(imgDir);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Define o caminho físico onde o arquivo será salvo
            String caminhoArquivo = imgDir + File.separator + produto.file().getOriginalFilename();

            // Salva no disco
            produto.file().transferTo(new File(caminhoArquivo));

            // Define o caminho da imagem no produto
            novoProduto.setImagePath(caminhoArquivo);
        }

        novoProduto.setDescricao(produto.descricao());
        novoProduto.setCodigo(produto.codigo());
        novoProduto.setPrecoUnitario(produto.precoUnitario());

        repository.save(novoProduto);
        return novoProduto;
    }

    public Optional<Produto> findById(Long id) {
        return repository.findById(id);
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public boolean update(Produto produto) {
        if (repository.existsById(((AbstractEntity) produto).getId())) {
            repository.save(produto);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
