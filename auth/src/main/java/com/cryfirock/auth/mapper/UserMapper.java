package com.cryfirock.auth.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;

/**
 * 1. Mapper para actualizar entidades User a partir de UserUpdateDto.
 * 2. Este intermediario es útil para actualizar parcialmente entidades User.
 * 3. Genera la implementación del mapper como un bean de Spring (@Component).
 * 4. Cuando el source tiene un campo null MapStruct NO lo copia al target.
 * 5. Si el target tiene campos sin mapear MapStruct no da warning ni error.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    /**
     * 1. Deja explícito si quieres el mapeo completo por defecto.
     * 2. Ignora los campos que no quieres mapear para actualizar.
     * 3. El id no se mapea para evitar cambios accidentales.
     * 4. El passwordHash no se mapea para evitar cambios accidentales.
     * 5. Los roles no se mapean para evitar cambios accidentales.
     * 6. Actualiza la entidad target con los valores del DTO.
     * 7. No retorna nada porque el target se actualiza in situ.
     *
     * @param target La entidad User a actualizar.
     * @param dto El DTO con los datos para actualizar.
     */
    @BeanMapping(ignoreByDefault = false) @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "passwordHash", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    // Actualiza la entidad target con los valores del DTO.
    void update(@MappingTarget User target, UserUpdateDto dto);
}
