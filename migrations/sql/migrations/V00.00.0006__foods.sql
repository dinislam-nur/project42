-- foods

CREATE TABLE foods
(
    food_id BIGSERIAL,
    food_name TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    picture TEXT NOT NULL,
    food_category_id INTEGER NOT NULL,
    CONSTRAINT pk_foods PRIMARY KEY (food_id),
    CONSTRAINT fk_food_categories FOREIGN KEY (food_category_id) REFERENCES food_categories (food_category_id),
    CONSTRAINT uq_foods_food_name UNIQUE (food_name)
);

-- index
CREATE INDEX foods_id_index ON foods (food_id);