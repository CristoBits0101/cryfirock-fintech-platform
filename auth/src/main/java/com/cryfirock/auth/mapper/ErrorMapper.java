package com.cryfirock.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.cryfirock.auth.model.ErrorResponse;

/**
 * 1. Mapper para convertir detalles de error en un objeto Error.
 * 2. Genera la implementación del mapper como un bean de Spring (@Component).
 * 3. Cuando el source tiene un campo null MapStruct no lo copia al target.
 * 4. Si el target tiene campos sin mapear MapStruct da warning y error.
 * 5. Este intermediario es útil para actualizar parcialmente entidades User.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ErrorMapper {
    /**
     * 1. Mapea el código de estado HTTP al campo status.
     * 2. Mapea el mensaje breve al campo error.
     * 3. Mapea los detalles adicionales al campo message.
     * 4. Establece la fecha actual en el campo date.
     * 5. Retorna el objeto Error mapeado.
     *
     * @param status Código de estado HTTP del error.
     * @param errorMessage Mensaje breve del error.
     * @param detail Detalles adicionales del error.
     * @return Objeto Error con la información mapeada.
     */
    @Mapping(target = "error", source = "errorMessage") @Mapping(target = "message", source = "detail") @Mapping(target = "status", source = "status") @Mapping(target = "date", expression = "java(new java.util.Date())")
    ErrorResponse toError(int status, String errorMessage, String detail);
}
