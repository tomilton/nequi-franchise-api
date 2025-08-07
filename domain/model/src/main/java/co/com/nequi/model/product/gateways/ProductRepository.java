package co.com.nequi.model.product.gateways;

import co.com.nequi.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {

  Mono<Product> save(Product product);
  
  Mono<Void> delete(Integer productId, Integer sucursalId);
  
  Mono<Product> updateStock(Integer productId, Integer newStock);
}
