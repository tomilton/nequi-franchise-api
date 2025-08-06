package co.com.nequi.mysql.service;

import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.mysql.mapper.FranchiseMapper;
import co.com.nequi.mysql.repository.FranchiseReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FranchiseDBRepository implements FranchiseRepository {

  private final FranchiseReactiveRepository franchiseReactiveRepository;
  private final FranchiseMapper franchiseMapper;

  @Override
  public Mono<Franchise> save(Franchise franchise) {
    return franchiseReactiveRepository
        .save(franchiseMapper.toEntity(franchise))
        .map(franchiseMapper::toDomain);
  }
}
