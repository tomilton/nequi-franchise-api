package co.com.nequi.mysql.repository;

import co.com.nequi.mysql.entity.Sucursal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SucursalReactiveRepository extends ReactiveCrudRepository<Sucursal, Integer> {
    Flux<Sucursal> findByFranchiseId(Integer franchiseId);
    Mono<Sucursal> findByFranchiseIdAndName(Integer franchiseId, String name);
} 