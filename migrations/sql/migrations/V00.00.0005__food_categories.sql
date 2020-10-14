-- Food category

CREATE TABLE food_categories
(
    food_category_id INTEGER,
    category_name VARCHAR(24) NOT NULL,
    CONSTRAINT pk_food_categories PRIMARY KEY (food_category_id),
    CONSTRAINT uq_food_categories_category_name UNIQUE (category_name)
);