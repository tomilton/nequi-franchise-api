package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
  Flux<ProductEntity> findBySucursalId(Integer sucursalId);

  Mono<ProductEntity> findBySucursalIdAndName(Integer sucursalId, String name);

  Flux<ProductEntity> findBySucursalIdOrderByStockDesc(Integer sucursalId);

  Mono<Void> deleteByIdAndSucursalId(Integer id, Integer sucursalId);

  @Query("UPDATE product SET stock = :newStock, updated_at = NOW() WHERE id = :id")
  Mono<Integer> updateStockById(Integer newStock, Integer id);
}
