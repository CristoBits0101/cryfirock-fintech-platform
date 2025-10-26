package com.cryfirock.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.cryfirock.auth.model.Error;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ErrorMapper {
  @Mapping(target = "error", source = "errorMessage")
  @Mapping(target = "message", source = "detail")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "date", expression = "java(new java.util.Date())")
  Error toError(int status, String errorMessage, String detail);
}
