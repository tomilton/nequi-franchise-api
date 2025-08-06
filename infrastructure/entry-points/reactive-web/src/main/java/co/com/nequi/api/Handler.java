package co.com.nequi.api;

import co.com.nequi.model.franchise.model.Franchise;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

  private final FranchiseUseCase franchiseUseCase;

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
}
