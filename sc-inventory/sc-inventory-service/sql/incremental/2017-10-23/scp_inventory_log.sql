CREATE TABLE scp_inventory_log
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    order_id VARCHAR(40),
    commerce_id VARCHAR(10),
    product_id VARCHAR(40),
    loc VARCHAR(40),
    quantity BIGINT,
    type VARCHAR(10),
    comment VARCHAR(1000),
    date_time TIMESTAMP
);