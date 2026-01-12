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
 * Mapper para actualizar entidades User a partir de UserUpdateDto.
 * 
 * {@code UserMapper}: Este intermediario es útil para actualizar parcialmente entidades User.
 * {@code componentModel}: Genera la implementación del mapper como un bean de Spring (@Component).
 * {@code nullValuePropertyMappingStrategy}: Cuando el source tiene un campo null MapStruct NO lo copia al target.
 * {@code unmappedTargetPolicy}: Si el target tiene campos sin mapear MapStruct no da warning ni error.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  /**
   * 1. Dejar explícito sí quieres el mapeo completo por defecto.
   * 2. Ignorar los campos que no quieres mapear para actualizar.
   * 3. El id no se mapea para evitar cambios accidentales.
   * 4. El passwordHash no se mapea para evitar cambios accidentales.
   * 5. Los roles no se mapean para evitar cambios accidentales.
   * 6. Actualiza la entidad target con los valores del dto.
   * 7. No retorna nada porque el target se actualiza in place.
   * 
   * @param target
   * @param dto
   */
  @BeanMapping(ignoreByDefault = false)
  @Mappings({
      @Mapping(target = "roles", ignore = true),
      @Mapping(target = "passwordHash", ignore = true),
      @Mapping(target = "id", ignore = true)
  })
  void update(@MappingTarget User target, UserUpdateDto dto);
}
