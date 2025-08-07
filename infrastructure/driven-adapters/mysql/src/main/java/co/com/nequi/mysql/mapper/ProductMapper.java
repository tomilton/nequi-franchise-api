package co.com.nequi.mysql.mapper;

import co.com.nequi.model.product.Product;
import co.com.nequi.mysql.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductEntity toEntity(final Product product);

  Product toDomain(final ProductEntity productEntity);
}
