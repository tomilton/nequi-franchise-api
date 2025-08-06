package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.Franchise;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseReactiveRepository extends ReactiveCrudRepository<Franchise, Integer> {
  Mono<Franchise> findByName(String name);
}
