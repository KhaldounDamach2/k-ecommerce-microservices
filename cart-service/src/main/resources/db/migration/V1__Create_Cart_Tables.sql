
-- Datei: V1__Create_Cart_Tables.sql
CREATE SEQUENCE IF NOT EXISTS cart_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS cart_item_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE cart (
    id BIGINT PRIMARY KEY DEFAULT nextval('cart_seq'),
    buyer_id BIGINT NOT NULL,
    total_price NUMERIC(19,2) DEFAULT 0 NOT NULL,
    checked_out BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE cart_item (
    id BIGINT PRIMARY KEY DEFAULT nextval('cart_item_seq'),
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(19,2) NOT NULL,
    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE
);
