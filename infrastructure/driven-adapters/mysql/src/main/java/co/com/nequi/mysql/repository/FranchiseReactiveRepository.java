package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, Integer> {
  Mono<FranchiseEntity> findByName(String name);
  
  @Query("UPDATE franchise SET name = :newName, updated_at = NOW() WHERE id = :id")
  Mono<Integer> updateNameById(String newName, Integer id);
}
