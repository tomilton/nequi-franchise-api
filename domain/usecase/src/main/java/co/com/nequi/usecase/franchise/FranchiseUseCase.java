package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.gateways.repositories.FranchiseRepository;
import co.com.nequi.model.franchise.model.Franchise;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

  private final FranchiseRepository franchiseRepository;

  public Mono<Franchise> save(Franchise franchise) {
    return franchiseRepository.save(franchise);
  }
}
