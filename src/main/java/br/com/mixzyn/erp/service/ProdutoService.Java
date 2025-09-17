package br.com.mixzyn.erp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.dto.ProdutoDTO;
import br.com.mixzyn.erp.model.Produto;
import br.com.mixzyn.erp.repository.ProdutoRepository;

@Service
public class ProdutoService extends AbstractService<Produto> {
    private ProdutoRepository repository;

    @Value("${app.imgDir}")
    private String imgDir;

    public ProdutoService(ProdutoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Produto create(Produto produto) {
        Produto novoProduto = produto;
        return repository.save(novoProduto);
    }

    public Produto createWithImage(ProdutoDTO produtoDTO) throws IllegalStateException, IOException {
        Produto produto = new Produto();

        produto.setDescricao(produtoDTO.descricao());
        produto.setCodigo(produtoDTO.codigo());
        produto.setPrecoUnitario(produtoDTO.precoUnitario());

        repository.save(produto);

        if (produtoDTO.imagePath() != null && !produtoDTO.imagePath().isEmpty()) {

            // Cria diretório se não existir
            File diretorio = new File(imgDir);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Extrai a extensão do arquivo original
            String originalFilename = produtoDTO.imagePath().getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // Define o nome padronizado: produto-{id}.extensão
            String nomeArquivo = "produto-" + produto.getId() + extension;
            String caminhoArquivo = imgDir + File.separator + nomeArquivo;

            // Salva no disco
            produtoDTO.imagePath().transferTo(new File(caminhoArquivo));

            // Salva apenas o caminho relativo no banco
            produto.setImagePath("imgs/" + nomeArquivo);

            // Atualiza o produto com o caminho da imagem
            produto = repository.save(produto);
        }

        return produto;
    }

    public Optional<Produto> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }

    public List<Produto> findByDescricao(String descricao) {
        return repository.findByDescricaoContainingIgnoreCase(descricao);
    }
}
