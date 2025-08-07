package co.com.nequi.usecase.sucursal;

import co.com.nequi.model.sucursal.Sucursal;
import co.com.nequi.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SucursalUseCase {

  private final SucursalRepository sucursalRepository;

  public Mono<Sucursal> save(Sucursal sucursal) {
    return sucursalRepository.save(sucursal);
  }
  
  public Mono<Sucursal> updateName(Integer sucursalId, String newName) {
    return sucursalRepository.updateName(sucursalId, newName);
  }
}
