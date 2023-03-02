CREATE TABLE customer_directory
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(64),
    last_name  VARCHAR(64),
    email      VARCHAR(64),
    phone      VARCHAR(64),
    password   VARCHAR(64)
);

CREATE TABLE product_catalog
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(64),
    description VARCHAR(512),
    image       VARCHAR(64),
    phone       VARCHAR(64),
    quantity    INT,
    PRICE       INT
);

CREATE TABLE shopping_cart
(
    id          BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES customer_directory
);

CREATE TABLE shopping_cart_item
(
    id                 BIGSERIAL PRIMARY KEY,
    shopping_cart_id   BIGINT REFERENCES shopping_cart,
    product_catalog_id BIGINT REFERENCES product_catalog,
    quantity           INT
);

CREATE TABLE customer_order_status
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(16)
);

CREATE TABLE customer_payment_method
(
    id             BIGSERIAL PRIMARY KEY,
    customer_id    BIGINT REFERENCES customer_directory,
    account_number INT,
    expiry_date    DATE
);

CREATE TABLE customer_order
(
    id                BIGSERIAL PRIMARY KEY,
    customer_id       BIGINT REFERENCES customer_directory,
    payment_method_id BIGINT REFERENCES customer_payment_method,
    order_status      VARCHAR(16),
    --     order_status_id   INT REFERENCES customer_order_status,
    order_total       INT,
    date_created      DATE
);
drop table customer_order;

CREATE TABLE customer_order_line
(
    id                 BIGSERIAL PRIMARY KEY,
    product_catalog_id BIGINT REFERENCES product_catalog,
    customer_order_id  BIGINT REFERENCES customer_order,
    quantity           INT,
    price              INT
);
