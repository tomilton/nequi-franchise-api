package co.com.nequi.usecase.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

  private final ProductRepository productRepository;

  public Mono<Product> save(Product product) {
    return productRepository.save(product);
  }

  public Mono<Void> delete(Integer productId, Integer sucursalId) {
    return productRepository.delete(productId, sucursalId);
  }
  
  public Mono<Product> updateStock(Integer productId, Integer newStock) {
    return productRepository.updateStock(productId, newStock);
  }
  
  public Flux<Product> findProductsWithMaxStockByFranchise(Integer franchiseId) {
    return productRepository.findProductsWithMaxStockByFranchise(franchiseId);
  }
  
  public Mono<Product> updateName(Integer productId, String newName) {
    return productRepository.updateName(productId, newName);
  }
}
