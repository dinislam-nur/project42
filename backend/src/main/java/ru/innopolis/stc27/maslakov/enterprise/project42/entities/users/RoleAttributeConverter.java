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
            case ROLE_CHIEF:
                return 1;
            case ROLE_WAITER:
                return 2;
            case ROLE_ADMIN:
                return 3;
            case ROLE_GUEST:
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
                return Role.ROLE_CHIEF;
            case 2:
                return Role.ROLE_WAITER;
            case 3:
                return Role.ROLE_ADMIN;
            case 4:
                return Role.ROLE_GUEST;
            default:
                throw new IllegalArgumentException("Нет соответствующей роли для значения: " + dbInteger);
        }
    }
}
