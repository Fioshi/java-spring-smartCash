CREATE TABLE tb_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tb_spent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type ENUM('CREDIT', 'DEBIT', 'PIX', 'INSTALLMENTS') NOT NULL,
    installments INT,
    value DOUBLE NOT NULL,
    place VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE SET NULL
);

CREATE TABLE tb_monthly_expense (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    month VARCHAR(20) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE
);

CREATE TABLE tb_monthly_expense_spents (
    monthly_expense_id BIGINT NOT NULL,
    spent_id BIGINT NOT NULL,
    PRIMARY KEY (monthly_expense_id, spent_id),
    FOREIGN KEY (monthly_expense_id) REFERENCES tb_monthly_expense(id) ON DELETE CASCADE,
    FOREIGN KEY (spent_id) REFERENCES tb_spent(id) ON DELETE CASCADE
);