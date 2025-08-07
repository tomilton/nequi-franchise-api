package co.com.nequi.mysql.mapper;

import co.com.nequi.model.sucursal.Sucursal;
import co.com.nequi.mysql.entity.SucursalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SucursalMapper {

  SucursalEntity toEntity(final Sucursal sucursal);

  Sucursal toDomain(final SucursalEntity sucursalEntity);
}
