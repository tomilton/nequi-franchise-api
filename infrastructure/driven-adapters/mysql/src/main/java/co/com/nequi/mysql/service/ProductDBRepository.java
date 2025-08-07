package co.com.nequi.mysql.service;

import co.com.nequi.model.enums.InfrastructureError;
import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import co.com.nequi.mysql.mapper.ProductMapper;
import co.com.nequi.mysql.repository.ProductReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
        .map(productMapper::toDomain)
        .onErrorResume(this::getError);
  }

  @Override
  public Mono<Void> delete(Integer productId, Integer sucursalId) {
    return productReactiveRepository
        .deleteByIdAndSucursalId(productId, sucursalId)
        .then()
        .onErrorResume(this::getError);
  }

  @Override
  public Mono<Product> updateStock(Integer productId, Integer newStock) {
    return productReactiveRepository
        .updateStockById(newStock, productId)
        .then(productReactiveRepository.findById(productId))
        .map(productMapper::toDomain)
        .onErrorResume(this::getError);
  }

  @Override
  public Flux<Product> findProductsWithMaxStockByFranchise(Integer franchiseId) {
    return productReactiveRepository
        .findProductsWithMaxStockByFranchise(franchiseId)
        .map(productMapper::toDomain)
        .onErrorResume(this::getError);
  }

  @Override
  public Mono<Product> updateName(Integer productId, String newName) {
    return productReactiveRepository
        .updateNameById(newName, productId)
        .then(productReactiveRepository.findById(productId))
        .map(productMapper::toDomain)
        .onErrorResume(this::getError);
  }

  private <T> Mono<T> getError(Throwable throwable) {
    return Mono.error(InfrastructureError.DB_ERROR.build(throwable));
  }
}
