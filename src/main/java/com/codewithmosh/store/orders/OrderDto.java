package com.codewithmosh.store.orders;

import com.codewithmosh.store.payments.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    public Long id;
    public PaymentStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
}
