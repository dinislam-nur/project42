package ru.innopolis.stc27.maslakov.enterprise.project42.entities.users;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleAttributeConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        switch (role) {
            case STAFF:
                return 1;
            case GUEST:
                return 2;
            default:
                throw new IllegalArgumentException("Нет соответсвующего значения для роли");
        }
    }

    @Override
    public Role convertToEntityAttribute(Integer dbInteger) {
        if (dbInteger == null) {
            return null;
        }
        switch (dbInteger) {
            case 1:
                return Role.STAFF;
            case 2:
                return Role.GUEST;
            default:
                throw new IllegalArgumentException("Нет соответсвующей роля для значения");
        }
    }
}
