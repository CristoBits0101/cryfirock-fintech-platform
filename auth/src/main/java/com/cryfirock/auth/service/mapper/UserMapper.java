package com.cryfirock.auth.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.cryfirock.auth.service.dto.UserUpdateDto;
import com.cryfirock.auth.service.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    void update(@MappingTarget User target, UserUpdateDto dto);
}
