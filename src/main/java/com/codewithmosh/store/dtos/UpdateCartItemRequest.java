package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "Product id is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    @Max(value = 100, message = "Quantity should be at most 100")
    private Integer quantity;
}
