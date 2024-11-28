-- Tabela de Fornecedores
CREATE TABLE fornecedor (
    id_fornecedor INT PRIMARY KEY AUTO_INCREMENT,
    nome_fornecedor VARCHAR(100) NOT NULL,
    pessoa_contato VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT
);

-- Tabela de Depósitos
CREATE TABLE deposito (
    id_deposito INT PRIMARY KEY AUTO_INCREMENT,
    nome_deposito VARCHAR(100) NOT NULL,
    localizacao VARCHAR(200),
    nome_gerente VARCHAR(100),
    telefone_contato VARCHAR(20)
);

-- Tabela de Clientes
CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nome_cliente VARCHAR(100) NOT NULL,
    pessoa_contato VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT
);

-- Tabela de Produtos
CREATE TABLE produto (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    nome_produto VARCHAR(200) NOT NULL,
    codigo_produto VARCHAR(50) UNIQUE NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(100),
    fabricante VARCHAR(100),
    CONSTRAINT chk_preco_positivo CHECK (preco_unitario >= 0)
);

-- Tabela de Estoque
CREATE TABLE estoque (
    id_estoque INT PRIMARY KEY AUTO_INCREMENT,
    id_produto INT NOT NULL,
    id_deposito INT NOT NULL,
    quantidade_disponivel INT NOT NULL DEFAULT 0,
    ultima_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    numero_lote VARCHAR(50),
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    FOREIGN KEY (id_deposito) REFERENCES deposito(id_deposito),
    CONSTRAINT chk_quantidade_positiva CHECK (quantidade_disponivel >= 0)
);

-- Tabela de Pedidos de Compra
CREATE TABLE pedido_compra (
    id_pedido_compra INT PRIMARY KEY AUTO_INCREMENT,
    id_fornecedor INT NOT NULL,
    data_pedido DATE NOT NULL,
    status ENUM('Pendente', 'Processado', 'Cancelado') DEFAULT 'Pendente',
    custo_total DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id_fornecedor),
    CONSTRAINT chk_custo_positivo CHECK (custo_total >= 0)
);

-- Tabela de Itens do Pedido de Compra
CREATE TABLE item_pedido_compra (
    id_item_pedido_compra INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido_compra INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido_compra) REFERENCES pedido_compra(id_pedido_compra),
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    CONSTRAINT chk_quantidade_compra_positiva CHECK (quantidade > 0),
    CONSTRAINT chk_preco_compra_positivo CHECK (preco_unitario >= 0)
);

-- Tabela de Pedidos de Venda
CREATE TABLE pedido_venda (
    id_pedido_venda INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    data_pedido DATE NOT NULL,
    status ENUM('Pendente', 'Enviado', 'Entregue', 'Cancelado') DEFAULT 'Pendente',
    valor_total DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT chk_valor_positivo CHECK (valor_total >= 0)
);

-- Tabela de Itens do Pedido de Venda
CREATE TABLE item_pedido_venda (
    id_item_pedido_venda INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido_venda INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido_venda) REFERENCES pedido_venda(id_pedido_venda),
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    CONSTRAINT chk_quantidade_venda_positiva CHECK (quantidade > 0),
    CONSTRAINT chk_preco_venda_positivo CHECK (preco_unitario >= 0)
);
