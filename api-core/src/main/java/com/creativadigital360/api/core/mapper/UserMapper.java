package com.creativadigital360.api.core.mapper;

import com.creativadigital360.api.core.dto.UserUpdateDto;
import com.creativadigital360.api.core.entity.User;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @BeanMapping(ignoreByDefault = false)
    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "passwordHash", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void update(@MappingTarget User target, UserUpdateDto dto);

}
