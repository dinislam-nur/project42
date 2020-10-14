package ru.innopolis.stc27.maslakov.enterprise.project42.entities.food;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FoodCategoryAttributeConverter implements AttributeConverter<FoodCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FoodCategory category) {
        if (category == null) {
            return null;
        }
        switch (category) {
            case DRINK:
                return 1;
            case HOT_DISHES:
                return 2;
            case SOMETHING_ELSE:
                return 3;
            default:
                throw new IllegalArgumentException("Нет соответствующего значения для категории еды: " + category);
        }
    }

    @Override
    public FoodCategory convertToEntityAttribute(Integer dbInteger) {
        if (dbInteger == null) {
            return null;
        }
        switch (dbInteger) {
            case 1:
                return FoodCategory.DRINK;
            case 2:
                return FoodCategory.HOT_DISHES;
            case 3:
                return FoodCategory.SOMETHING_ELSE;
            default:
                throw new IllegalArgumentException("Нет соответствующей категории еды для значения: " + dbInteger);
        }
    }
}
