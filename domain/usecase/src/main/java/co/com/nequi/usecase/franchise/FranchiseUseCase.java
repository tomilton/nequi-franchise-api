package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.franchise.Franchise;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

  private final FranchiseRepository franchiseRepository;

  public Mono<Franchise> save(Franchise franchise) {
    return franchiseRepository.save(franchise);
  }
}
