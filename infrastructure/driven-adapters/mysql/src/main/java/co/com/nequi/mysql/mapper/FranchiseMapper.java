package co.com.nequi.mysql.mapper;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.mysql.entity.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

  FranchiseEntity toEntity(final Franchise franchise);

  Franchise toDomain(final FranchiseEntity franchiseEntity);
}
