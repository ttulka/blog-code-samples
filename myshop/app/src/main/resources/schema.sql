DROP TABLE products IF EXISTS;
DROP TABLE orders IF EXISTS;

CREATE TABLE products (
  id          BIGINT IDENTITY PRIMARY KEY,
  name        VARCHAR(80),
  description VARCHAR(50),
  price       DECIMAL(10,2)
);
CREATE INDEX idx_products_name ON products (name);

CREATE TABLE orders (
  id        BIGINT IDENTITY PRIMARY KEY,
  customer  VARCHAR(50)
);
CREATE INDEX idx_orders_customer ON orders (customer);

CREATE TABLE orders_products (
  order_id    BIGINT NOT NULL,
  product_id  BIGINT NOT NULL
);
ALTER TABLE orders_products ADD CONSTRAINT fk_orders_products_order FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE orders_products ADD CONSTRAINT fk_orders_products_product FOREIGN KEY (product_id) REFERENCES products (id);
