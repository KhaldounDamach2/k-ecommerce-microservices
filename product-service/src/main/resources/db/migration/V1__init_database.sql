-- V1__init.sql
-- Flyway migration to initialize the schema for all entities in the e-commerce domain

CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS client_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_attribute_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_variant_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_variant_attribute_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_variant_photo_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE category (
    id BIGINT PRIMARY KEY DEFAULT nextval('category_seq'),
    cat_name VARCHAR(255) NOT NULL UNIQUE,
    cat_descrip VARCHAR(255) NULL
);

CREATE TABLE client (
    id BIGINT PRIMARY KEY DEFAULT nextval('client_seq'),
    client_name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE product (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_seq'),
    prod_name VARCHAR(255) NOT NULL,
    prod_descrip VARCHAR(255) NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    category_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id),
    CONSTRAINT fk_product_client FOREIGN KEY (client_id) REFERENCES client(id),
    CONSTRAINT uq_client_prod_categ UNIQUE (client_id, prod_name, category_id)
);

CREATE TABLE product_attribute (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_attribute_seq'),
    attr_name VARCHAR(255) NULL
);

CREATE TABLE product_variant (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_variant_seq'),
    sku VARCHAR(255) NOT NULL,
    price NUMERIC(19,2) NOT NULL,
    stock INTEGER NOT NULL,
    manuf_date DATE NULL,
    model VARCHAR(255) NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_variant_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT uq_product_variant UNIQUE (product_id, sku)
);

CREATE TABLE product_variant_attribute (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_variant_attribute_seq'),
    value VARCHAR(255) NULL,
    prod_variant_id BIGINT NULL,
    attribute_id BIGINT NULL,
    CONSTRAINT fk_varattr_variant FOREIGN KEY (prod_variant_id) REFERENCES product_variant(id),
    CONSTRAINT fk_varattr_attribute FOREIGN KEY (attribute_id) REFERENCES product_attribute(id),
    CONSTRAINT uq_attribute_variant UNIQUE (prod_variant_id, attribute_id)
);

CREATE TABLE product_variant_photo (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_variant_photo_seq'),
    url VARCHAR(255) NOT NULL,
    alt_text VARCHAR(255) NULL,
    position INTEGER NOT NULL,
    prod_variant_id BIGINT NOT NULL,
    CONSTRAINT fk_photo_variant FOREIGN KEY (prod_variant_id) REFERENCES product_variant(id),
    CONSTRAINT uq_product_variant_photo UNIQUE (prod_variant_id, url)
);
