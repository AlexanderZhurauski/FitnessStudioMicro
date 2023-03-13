CREATE TABLE IF NOT EXISTS app.product (
    id UUID,
    creation_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    last_updated TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    title TEXT NOT NULL,
    weight INT NOT NULL,
    calories INT NOT NULL,
    proteins DECIMAL(6,1) NOT NULL,
    fats DECIMAL(6,1) NOT NULL,
    carbohydrates DECIMAL(6,1) NOT NULL,
    CONSTRAINT pk_product_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS app.product_instance (
    id UUID,
    product_id UUID,
    weight INT NOT NULL,
    CONSTRAINT pk_product_instance_id PRIMARY KEY (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id)
        REFERENCES app.product (id)
);