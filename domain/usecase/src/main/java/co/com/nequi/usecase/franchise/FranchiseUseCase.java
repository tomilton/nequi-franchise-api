package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.exception.BusinessException;
import co.com.nequi.model.enums.BusinessErrorMessage;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

  private final FranchiseRepository franchiseRepository;

  public Mono<Franchise> save(Franchise franchise) {
    return franchiseRepository.save(franchise);
  }

  public Mono<Franchise> updateName(Integer franchiseId, String newName) {
    return franchiseRepository.findById(franchiseId)
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.FRANCHISE_UPDATE_NOT_FOUND)))
        .flatMap(franchise -> franchiseRepository.updateName(franchiseId, newName));
  }
}
