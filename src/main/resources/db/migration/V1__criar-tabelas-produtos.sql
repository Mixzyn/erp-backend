-- Tabela de Fornecedores
CREATE TABLE tb_fornecedor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_fornecedor VARCHAR(100) NOT NULL,
    pessoa_contato VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT
);

-- Tabela de Depósitos
/* CREATE TABLE tb_deposito (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_deposito VARCHAR(100) NOT NULL,
    localizacao VARCHAR(200),
    nome_gerente VARCHAR(100),
    telefone_contato VARCHAR(20)
); */

-- Tabela de Clientes
/* CREATE TABLE tb_cliente (
    id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_cliente VARCHAR(100) NOT NULL,
    pessoa_contato VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT
); */

-- Tabela de Produtos
CREATE TABLE tb_produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(200) NOT NULL,
    codigo VARCHAR(50) UNIQUE,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(100),
    fabricante VARCHAR(100),
    CONSTRAINT chk_preco_positivo CHECK (preco_unitario >= 0)
);

-- Tabela de Estoque
CREATE TABLE tb_estoque (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_produto BIGINT NOT NULL,
    -- id_deposito BIGINT NOT NULL,
    quantidade_disponivel INT NOT NULL DEFAULT 0,
    ultima_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    numero_lote VARCHAR(50),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id),
    -- FOREIGN KEY (id_deposito) REFERENCES tb_deposito(id),
    CONSTRAINT chk_quantidade_positiva CHECK (quantidade_disponivel >= 0)
);

-- Tabela de Pedidos de Compra
-- Cada produto no pedido é registrado na tabela item_compra
CREATE TABLE tb_compra (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_fornecedor BIGINT NOT NULL,
    data DATE NOT NULL,
    status ENUM('Pendente', 'Processado', 'Cancelado') DEFAULT 'Pendente',
    custo_total DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (id_fornecedor) REFERENCES tb_fornecedor(id),
    CONSTRAINT chk_custo_positivo CHECK (custo_total >= 0)
);

-- Tabela de Itens do Pedido de Compra
-- Essa tabela especifica os itens selecionados na compra
CREATE TABLE tb_item_compra (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_compra BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_compra) REFERENCES tb_compra(id),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id),
    CONSTRAINT chk_quantidade_compra_positiva CHECK (quantidade > 0),
    CONSTRAINT chk_preco_compra_positivo CHECK (preco_unitario >= 0)
);

-- Tabela de Pedidos de Venda
-- Cada produto no pedido é registrado na tabela item_venda
CREATE TABLE tb_venda (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    -- id_cliente BIGINT NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- status ENUM('PENDENTE', 'ENVIADO', 'ENTREGUE', 'CANCELADO') DEFAULT 'PENDENTE',
    valor_total DECIMAL(12, 2) NOT NULL,
    -- FOREIGN KEY (id_cliente) REFERENCES tb_cliente(id),
    CONSTRAINT chk_valor_positivo CHECK (valor_total >= 0)
);

-- Tabela de Itens do Pedido de Venda
-- Essa tabela especifica os itens selecionados na venda
CREATE TABLE tb_item_venda (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_venda BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_venda) REFERENCES tb_venda(id),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id),
    CONSTRAINT chk_quantidade_venda_positiva CHECK (quantidade > 0),
    CONSTRAINT chk_preco_venda_positivo CHECK (preco_unitario >= 0)
);

/* CREATE TABLE NOTA_FISCAL_VENDA (
    id_nota_fiscal_venda BIGINT PRIMARY KEY AUTO_INCREMENT
)

CREATE TABLE NOTA_FISCAL_COMPRA (
    id_nota_fiscal_compra BIGINT PRIMARY KEY AUTO_INCREMENT
) */

CREATE TABLE tb_user (
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(60) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE tb_role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name ENUM('ADMIN', 'BASIC') NOT NULL
);

CREATE TABLE tb_user_role (
    user_id UUID NOT NULL,
    role_id BIGINT NOT NULL
);


INSERT INTO tb_role (id, name) VALUES (1, 'ADMIN');
INSERT INTO tb_role (id, name) VALUES (2, 'BASIC');