package co.com.nequi.mysql.service;

import co.com.nequi.model.enums.InfrastructureError;
import co.com.nequi.model.sucursal.Sucursal;
import co.com.nequi.model.sucursal.gateways.SucursalRepository;
import co.com.nequi.mysql.mapper.SucursalMapper;
import co.com.nequi.mysql.repository.SucursalReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SucursalDBRepository implements SucursalRepository {

  private final SucursalReactiveRepository sucursalReactiveRepository;
  private final SucursalMapper sucursalMapper;

  @Override
  public Mono<Sucursal> save(Sucursal sucursal) {
    return sucursalReactiveRepository
        .save(sucursalMapper.toEntity(sucursal))
        .map(sucursalMapper::toDomain)
        .onErrorResume(this::getError);
  }

  @Override
  public Mono<Sucursal> updateName(Integer sucursalId, String newName) {
    return sucursalReactiveRepository
        .updateNameById(newName, sucursalId)
        .then(sucursalReactiveRepository.findById(sucursalId))
        .map(sucursalMapper::toDomain)
        .onErrorResume(this::getError);
  }

  private <T> Mono<T> getError(Throwable throwable) {
    return Mono.error(InfrastructureError.DB_ERROR.build(throwable));
  }
}
