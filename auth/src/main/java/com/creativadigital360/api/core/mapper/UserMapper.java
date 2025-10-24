package com.creativadigital360.api.core.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.creativadigital360.api.core.dto.UserUpdateDto;
import com.creativadigital360.api.core.entity.User;

/**
 * =============================================================================================================================
 * Paso 12.1: Mapper de actualización parcial de DTO a Entidad
 * =============================================================================================================================
 */
// MapStruct crea algo como UserMapperImpl automáticamente
// Evita escribir setters o constructores manuales para actualizar atributos
// Aplica solo los campos no nulos del DTO
// La implementación UserMapperImpl la genera MapStruct en compilación
// Spring la inyecta como bean
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * =========================================================================================================================
     * Paso 12.2: Métodos
     * =========================================================================================================================
     */
    @BeanMapping(ignoreByDefault = false)
    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "passwordHash", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void update(@MappingTarget User target, UserUpdateDto dto);
}
