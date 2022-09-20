USE stefaninifood;
GO

CREATE TABLE customer (
	id INTEGER IDENTITY primary key,
	name VARCHAR(255) NOT NULL,
	CPF CHAR(11) UNIQUE
);

CREATE TABLE company (
	id INTEGER IDENTITY primary key,
	name VARCHAR(255) NOT NULL,
	cnpj CHAR(14) NOT NULL
);

CREATE TABLE address (
	id INTEGER IDENTITY PRIMARY KEY,
	logradouro VARCHAR(255) NOT NULL,
	estado VARCHAR(255) NOT NULL,
	pais VARCHAR(255) NOT NULL,
	cep CHAR(8) NOT NULL,
	customer_id INTEGER REFERENCES customer(id),
	company_id INTEGER REFERENCES company(id)
);

CREATE TABLE wallet (
	id INTEGER IDENTITY PRIMARY KEY,
	customer_id INTEGER NOT NULL REFERENCES customer(id),
	cash REAL
);

CREATE TABLE credit_card (
	id INTEGER IDENTITY PRIMARY KEY,
	wallet_id INTEGER NOT NULL REFERENCES wallet(id),
	card_number CHAR(16) NOT NULL UNIQUE,
	expiration_date DATE NOT NULL,
	cvv CHAR(3) NOT NULL
);

CREATE TABLE product (
	id INTEGER IDENTITY primary key,
	name VARCHAR(255) NOT NULL,
	description TEXT,
	price REAL,
	company_id INTEGER NOT NULL REFERENCES company(id)
);

CREATE TABLE order_details (
	id INTEGER IDENTITY primary key,
	customer_id INTEGER NOT NULL REFERENCES customer(id),
	payment_method VARCHAR(30) NOT NULL,
	total_price REAL,
	order_status VARCHAR(15) NOT NULL,
	createdAt TIMESTAMP NOT NULL
);

CREATE TABLE delivery (
	id INTEGER IDENTITY PRIMARY KEY,
	order_id INTEGER REFERENCES order_details(id),
	customer_address INTEGER REFERENCES address(id),
	company_address INTEGER REFERENCES address(id),
);

CREATE TABLE order_product (
	id INTEGER IDENTITY primary key,
	order_id INTEGER NOT NULL REFERENCES order_details(id),
	product_id INTEGER NOT NULL REFERENCES product(id),
	price REAL NOT NULL,
	quantity INTEGER NOT NULL
);

GO
-------------------------------------------------------------------

CREATE INDEX idx_customer ON customer(name);
CREATE INDEX idx_product ON product(name);
CREATE INDEX idx_company ON company(name);
CREATE INDEX idx_order_status ON order_details(order_status);

GO