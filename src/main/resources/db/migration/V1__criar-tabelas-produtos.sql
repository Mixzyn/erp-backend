-- Tabela de Fornecedores
CREATE TABLE fornecedor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_fornecedor VARCHAR(100) NOT NULL,
    pessoa_contato VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT
);

-- Tabela de Depósitos
CREATE TABLE deposito (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_deposito VARCHAR(100) NOT NULL,
    localizacao VARCHAR(200),
    nome_gerente VARCHAR(100),
    telefone_contato VARCHAR(20)
);

-- Tabela de Clientes
/* CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nome_cliente VARCHAR(100) NOT NULL,
    pessoa_contato VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT
); */

-- Tabela de Produtos
CREATE TABLE produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(200) NOT NULL,
    codigo VARCHAR(50) UNIQUE,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(100),
    fabricante VARCHAR(100),
    CONSTRAINT chk_preco_positivo CHECK (preco_unitario >= 0)
);

-- Tabela de Estoque
CREATE TABLE estoque (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_produto INT NOT NULL,
    id_deposito INT NOT NULL,
    quantidade_disponivel INT NOT NULL DEFAULT 0,
    ultima_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    numero_lote VARCHAR(50),
    FOREIGN KEY (id_produto) REFERENCES produto(id),
    FOREIGN KEY (id_deposito) REFERENCES deposito(id),
    CONSTRAINT chk_quantidade_positiva CHECK (quantidade_disponivel >= 0)
);

-- Tabela de Pedidos de Compra
-- Cada produto no pedido é registrado na tabela item_compra
CREATE TABLE compra (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_fornecedor INT NOT NULL,
    data DATE NOT NULL,
    status ENUM('Pendente', 'Processado', 'Cancelado') DEFAULT 'Pendente',
    custo_total DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id),
    CONSTRAINT chk_custo_positivo CHECK (custo_total >= 0)
);

-- Tabela de Itens do Pedido de Compra
-- Essa tabela especifica os itens selecionados na compra
CREATE TABLE item_compra (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_compra INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_compra) REFERENCES compra(id),
    FOREIGN KEY (id_produto) REFERENCES produto(id),
    CONSTRAINT chk_quantidade_compra_positiva CHECK (quantidade > 0),
    CONSTRAINT chk_preco_compra_positivo CHECK (preco_unitario >= 0)
);

-- Tabela de Pedidos de Venda
-- Cada produto no pedido é registrado na tabela item_venda
CREATE TABLE venda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    -- id_cliente INT NOT NULL,
    data DATE NOT NULL,
    status ENUM('Pendente', 'Enviado', 'Entregue', 'Cancelado') DEFAULT 'Pendente',
    valor_total DECIMAL(12, 2) NOT NULL,
    -- FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    CONSTRAINT chk_valor_positivo CHECK (valor_total >= 0)
);

-- Tabela de Itens do Pedido de Venda
-- Essa tabela especifica os itens selecionados na venda
CREATE TABLE item_venda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_venda INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_venda) REFERENCES venda(id),
    FOREIGN KEY (id_produto) REFERENCES produto(id),
    CONSTRAINT chk_quantidade_venda_positiva CHECK (quantidade > 0),
    CONSTRAINT chk_preco_venda_positivo CHECK (preco_unitario >= 0)
);

/* CREATE TABLE NOTA_FISCAL_VENDA (
    id_nota_fiscal_venda INT PRIMARY KEY AUTO_INCREMENT
)

CREATE TABLE NOTA_FISCAL_COMPRA (
    id_nota_fiscal_compra INT PRIMARY KEY AUTO_INCREMENT
) */