package co.com.nequi.mysql.service;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import co.com.nequi.mysql.mapper.ProductMapper;
import co.com.nequi.mysql.repository.ProductReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductDBRepository implements ProductRepository {

  private final ProductReactiveRepository productReactiveRepository;
  private final ProductMapper productMapper;

  @Override
  public Mono<Product> save(Product product) {
    return productReactiveRepository
        .save(productMapper.toEntity(product))
        .map(productMapper::toDomain);
  }

  @Override
  public Mono<Void> delete(Integer productId, Integer sucursalId) {
    return productReactiveRepository.deleteByIdAndSucursalId(productId, sucursalId).then();
  }

  @Override
  public Mono<Product> updateStock(Integer productId, Integer newStock) {
    return productReactiveRepository
        .updateStockById(newStock, productId)
        .then(productReactiveRepository.findById(productId))
        .map(productMapper::toDomain);
  }
}
