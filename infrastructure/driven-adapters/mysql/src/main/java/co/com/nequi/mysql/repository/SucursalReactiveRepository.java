package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.SucursalEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SucursalReactiveRepository extends ReactiveCrudRepository<SucursalEntity, Integer> {
  Flux<SucursalEntity> findByFranchiseId(Integer franchiseId);

  Mono<SucursalEntity> findByFranchiseIdAndName(Integer franchiseId, String name);
  
  @Query("UPDATE sucursal SET name = :newName, updated_at = NOW() WHERE id = :id")
  Mono<Integer> updateNameById(String newName, Integer id);
}
