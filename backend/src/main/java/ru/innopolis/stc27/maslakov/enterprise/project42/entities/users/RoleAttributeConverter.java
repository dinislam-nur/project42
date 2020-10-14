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
            case CHIEF:
                return 1;
            case WAITER:
                return 2;
            case ADMIN:
                return 3;
            case GUEST:
                return 4;
            default:
                throw new IllegalArgumentException("Нет соответствующего значения для роли: " + role);
        }
    }

    @Override
    public Role convertToEntityAttribute(Integer dbInteger) {
        if (dbInteger == null) {
            return null;
        }
        switch (dbInteger) {
            case 1:
                return Role.CHIEF;
            case 2:
                return Role.WAITER;
            case 3:
                return Role.ADMIN;
            case 4:
                return Role.GUEST;
            default:
                throw new IllegalArgumentException("Нет соответствующей роли для значения: " + dbInteger);
        }
    }
}
