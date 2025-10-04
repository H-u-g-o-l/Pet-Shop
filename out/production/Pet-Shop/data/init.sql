CREATE TABLE IF NOT EXISTS clientes(
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS funcionarios(
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    cargo TEXT NOT NULL DEFAULT 'funcionario',
    ativo INTEGER NOT NULL DEFAULT 1,
    contratado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    demitido_em DATETIME NULL
);

CREATE TABLE IF NOT EXISTS lista_de_espera(
    id INTEGER PRIMARY KEY,
    pet_id INTEGER REFERENCES pets(id),
    pedido_feito DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pedidos_concluidos(
    id INTEGER PRIMARY KEY,
    func_id INTEGER NOT NULL REFERENCES funcionarios(id),
    pet_id INTEGER REFERENCES pets(id),
    banho BOOLEAN,
    tosa BOOLEAN,
    data_conclusao DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pets(
    id INTEGER PRIMARY KEY,
    user_id INTEGER REFERENCES users(id), 
    nome TEXT NOT NULL,
    raca TEXT NOT NULL,
    banho BOOLEAN,
    tosa BOOLEAN
);


INSERT INTO clientes (nome, email) VALUES ('ruan', 'juan@gmail.com');

INSERT INTO funcionarios (nome, email, cargo) VALUES ('hugo', 'emailpratrabalho132@gmail.com', 'gerente');
