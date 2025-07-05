package com.algofusion.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.algofusion.entities.User;
import com.algofusion.request.UserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "refreshTokenExpiresAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(UserRequest userRequest);
}
