CREATE TABLE IF NOT EXISTS app.recipe (
    id UUID,
    creation_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    last_updated TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    title TEXT NOT NULL,
    CONSTRAINT recipe_pk_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS app.recipe_product (
    recipe_id UUID,
    product_instance_id UUID,
    CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
        REFERENCES app.recipe (id),
    CONSTRAINT fk_product_instance_id FOREIGN KEY (product_instance_id)
        REFERENCES app.product_instance (id)
);

