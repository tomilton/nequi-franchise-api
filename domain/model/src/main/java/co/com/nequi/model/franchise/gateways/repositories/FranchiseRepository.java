package co.com.nequi.model.franchise.gateways.repositories;

import co.com.nequi.model.franchise.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {

  Mono<Franchise> save(Franchise franchise);
}
