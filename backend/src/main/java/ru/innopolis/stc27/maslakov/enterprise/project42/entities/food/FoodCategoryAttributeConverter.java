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
            case DRINKS:
                return 1;
            case HOT_DISHES:
                return 2;
            case DESSERTS:
                return 3;
            case SALADS:
                return 4;
            case SOUPS:
                return 5;
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
                return FoodCategory.DRINKS;
            case 2:
                return FoodCategory.HOT_DISHES;
            case 3:
                return FoodCategory.DESSERTS;
            case 4:
                return FoodCategory.SALADS;
            case 5:
                return FoodCategory.SOUPS;
            default:
                throw new IllegalArgumentException("Нет соответствующей категории еды для значения: " + dbInteger);
        }
    }
}
