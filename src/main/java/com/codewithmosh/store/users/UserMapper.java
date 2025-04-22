package com.codewithmosh.store.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest userDto);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
