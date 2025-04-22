package com.codewithmosh.store.payments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
