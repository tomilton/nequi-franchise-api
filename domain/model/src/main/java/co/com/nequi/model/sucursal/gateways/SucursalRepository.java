package co.com.nequi.model.sucursal.gateways;

import co.com.nequi.model.sucursal.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalRepository {

  Mono<Sucursal> save(Sucursal sucursal);
}
