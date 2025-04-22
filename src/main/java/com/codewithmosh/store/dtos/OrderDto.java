package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    public Long id;
    public OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
}
