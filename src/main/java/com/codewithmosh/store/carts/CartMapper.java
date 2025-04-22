package com.codewithmosh.store.carts;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    CartDto toDto(Cart cart);

    CartItemDto toDto(CartItem item);
}
