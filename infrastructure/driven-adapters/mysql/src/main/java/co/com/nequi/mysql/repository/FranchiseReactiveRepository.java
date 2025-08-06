package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, Integer> {
  Mono<FranchiseEntity> findByName(String name);
}
