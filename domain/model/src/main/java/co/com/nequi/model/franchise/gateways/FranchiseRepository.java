package co.com.nequi.model.franchise.gateways;

import co.com.nequi.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {

  Mono<Franchise> save(Franchise franchise);

  Mono<Franchise> updateName(Integer franchiseId, String newName);

  Mono<Franchise> findById(Integer franchiseId);
}
