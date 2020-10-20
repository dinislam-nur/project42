package ru.innopolis.stc27.maslakov.enterprise.project42.utils;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SessionDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.UserDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.UUID;
import java.util.stream.Collectors;

public class DTOConverter {

    public static OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getOrderTime(),
                order.getStatus(),
                order.getFoods().stream().map(Food::getId).collect(Collectors.toList()),
                order.getTable().getId(),
                order.isPayed(),
                order.getTotalSum()
        );
    }

    public static TableDTO convertToDTO(Table table) {
        return new TableDTO(
                table.getId(),
                table.getNumber(),
                table.getStatus()
        );
    }

    public static SessionDTO convertToDTO(Session session) {
        return SessionDTO.builder()
                .tableId(session.getTable() == null ? null : session.getTable().getId())
                .user(convertToDTO(session.getUser()))
                .token(session.getToken())
                .build();
    }

    public static UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
}
