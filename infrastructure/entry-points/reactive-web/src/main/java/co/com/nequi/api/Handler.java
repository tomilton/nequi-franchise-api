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

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {

  private final FranchiseUseCase franchiseUseCase;
  private final SucursalUseCase sucursalUseCase;
  private final ProductUseCase productUseCase;

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

  public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
    Integer productId = Integer.valueOf(serverRequest.pathVariable("productId"));
    Integer sucursalId = Integer.valueOf(serverRequest.pathVariable("sucursalId"));

    return productUseCase
        .delete(productId, sucursalId)
        .then(ServerResponse.ok().bodyValue("Producto eliminado exitosamente"))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al eliminar el producto: " + error.getMessage()));
  }

  public Mono<ServerResponse> updateProductStock(ServerRequest serverRequest) {
    Integer productId = Integer.valueOf(serverRequest.pathVariable("productId"));
    
    return serverRequest
        .bodyToMono(Map.class)
        .flatMap(body -> {
          Integer newStock = Integer.valueOf(body.get("stock").toString());
          return productUseCase.updateStock(productId, newStock);
        })
        .flatMap(product -> ServerResponse.ok().bodyValue(product))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al actualizar el stock del producto: " + error.getMessage()));
  }

  public Mono<ServerResponse> getProductsWithMaxStockByFranchise(ServerRequest serverRequest) {
    Integer franchiseId = Integer.valueOf(serverRequest.pathVariable("franchiseId"));
    
    return productUseCase
        .findProductsWithMaxStockByFranchise(franchiseId)
        .collectList()
        .flatMap(products -> ServerResponse.ok().bodyValue(products))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al obtener productos con máximo stock: " + error.getMessage()));
  }

  public Mono<ServerResponse> updateFranchiseName(ServerRequest serverRequest) {
    Integer franchiseId = Integer.valueOf(serverRequest.pathVariable("franchiseId"));
    
    return serverRequest
        .bodyToMono(Map.class)
        .flatMap(body -> {
          String newName = body.get("name").toString();
          return franchiseUseCase.updateName(franchiseId, newName);
        })
        .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
        .onErrorResume(
            error ->
                ServerResponse.badRequest()
                    .bodyValue("Error al actualizar el nombre de la franquicia: " + error.getMessage()));
  }
}
