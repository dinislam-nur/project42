package ru.innopolis.stc27.maslakov.enterprise.project42.utils;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;

import java.util.List;
import java.util.Objects;

public class OrderServiceUtils {
    public static void checkOrderDTO(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDTO - null");
        } else {
            if (orderDTO.getUserId() == null) {
                throw new IllegalStateException("В orderDTO поле userId == null");
            }
            if (orderDTO.getTableId() == null) {
                throw new IllegalStateException("В orderDTO поле tableId == null");
            }
            if (orderDTO.getPayed() == null) {
                throw new IllegalStateException("В orderDTO поле payed == null");
            }
            if (orderDTO.getTotal() == null) {
                throw new IllegalStateException("В orderDTO поле total == null");
            }
            final List<Long> foodsId = orderDTO.getFoodsId();
            if (foodsId == null) {
                throw new IllegalStateException("В orderDTO поле foodsId == null");
            } else if (foodsId.size() == 0) {
                throw new IllegalStateException("Список блюд пустой");
            } else if (!foodsId.stream().allMatch(Objects::nonNull)) {
                    throw new IllegalStateException("Nullable id у блюда");
                }
        }
    }
}
