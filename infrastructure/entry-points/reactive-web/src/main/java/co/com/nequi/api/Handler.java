package co.com.nequi.api;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.product.Product;
import co.com.nequi.model.sucursal.Sucursal;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import co.com.nequi.usecase.product.ProductUseCase;
import co.com.nequi.usecase.sucursal.SucursalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

  private final FranchiseUseCase franchiseUseCase;
  private final SucursalUseCase sucursalUseCase;
  private final ProductUseCase productUseCase;

  public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
    // useCase.logic();
    return ServerResponse.ok().bodyValue("");
  }

  public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
    // useCase2.logic();
    return ServerResponse.ok().bodyValue("");
  }

  public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
    // useCase.logic();
    return ServerResponse.ok().bodyValue("");
  }

  public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
    return serverRequest
        .bodyToMono(Franchise.class)
        .flatMap(franchiseUseCase::save)
        .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al crear la franquicia: " + error.getMessage()));
  }

  public Mono<ServerResponse> createSucursal(ServerRequest serverRequest) {
    return serverRequest
        .bodyToMono(Sucursal.class)
        .flatMap(sucursalUseCase::save)
        .flatMap(sucursal -> ServerResponse.ok().bodyValue(sucursal))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al crear la sucursal: " + error.getMessage()));
  }

  public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
    return serverRequest
        .bodyToMono(Product.class)
        .flatMap(productUseCase::save)
        .flatMap(sucursal -> ServerResponse.ok().bodyValue(sucursal))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al crear la product: " + error.getMessage()));
  }
}
