package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductReactiveRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findBySucursalId(Integer branchId);
    Mono<Product> findBySucursalIdAndName(Integer sucursalId, String name);
    Flux<Product> findBySucursalIdOrderByStockDesc(Integer branchId);
} 