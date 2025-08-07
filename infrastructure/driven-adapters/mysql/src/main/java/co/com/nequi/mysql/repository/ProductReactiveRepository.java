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
  
  @Query("""
      SELECT p.* FROM product p
      INNER JOIN sucursal s ON p.sucursal_id = s.id
      WHERE s.franchise_id = :franchiseId
      AND p.stock = (
          SELECT MAX(p2.stock)
          FROM product p2
          WHERE p2.sucursal_id = p.sucursal_id
      )
      ORDER BY s.id, p.stock DESC
      """)
  Flux<ProductEntity> findProductsWithMaxStockByFranchise(Integer franchiseId);
}
