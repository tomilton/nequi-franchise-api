package co.com.nequi.api;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.product.Product;
import co.com.nequi.model.sucursal.Sucursal;
import co.com.nequi.model.exception.InfrastructureException;
import co.com.nequi.model.exception.ErrorResponse;
import co.com.nequi.model.enums.InfrastructureError;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import co.com.nequi.usecase.product.ProductUseCase;
import co.com.nequi.usecase.sucursal.SucursalUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class, ErrorHandler.class})
@WebFluxTest
class RouterRestTest {

  @Autowired private WebTestClient webTestClient;

  @MockBean private FranchiseUseCase franchiseUseCase;

  @MockBean private SucursalUseCase sucursalUseCase;

  @MockBean private ProductUseCase productUseCase;

  @Test
  void testCreateFranchise_ShouldReturnSuccess() {
    // Arrange
    Franchise franchise = Franchise.builder().name("Test Franchise").build();
    when(franchiseUseCase.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

    // Act & Assert
    webTestClient
        .post()
        .uri("/api/franchise")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(franchise)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Franchise.class)
        .value(response -> assertEquals("Test Franchise", response.getName()));
  }

  @Test
  void testCreateSucursal_ShouldReturnSuccess() {
    // Arrange
    Sucursal sucursal = Sucursal.builder().franchiseId(1).name("Test Sucursal").build();
    when(sucursalUseCase.save(any(Sucursal.class))).thenReturn(Mono.just(sucursal));

    // Act & Assert
    webTestClient
        .post()
        .uri("/api/sucursal")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(sucursal)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Sucursal.class)
        .value(
            response -> {
              assertEquals("Test Sucursal", response.getName());
              assertEquals(1, response.getFranchiseId());
            });
  }

  @Test
  void testCreateProduct_ShouldReturnSuccess() {
    // Arrange
    Product product = Product.builder().sucursalId(1).name("Test Product").stock(100).build();
    when(productUseCase.save(any(Product.class))).thenReturn(Mono.just(product));

    // Act & Assert
    webTestClient
        .post()
        .uri("/api/product")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(product)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .value(
            response -> {
              assertEquals("Test Product", response.getName());
              assertEquals(1, response.getSucursalId());
              assertEquals(100, response.getStock());
            });
  }

  @Test
  void testDeleteProduct_ShouldReturnSuccess() {
    // Arrange
    when(productUseCase.delete(anyInt(), anyInt())).thenReturn(Mono.empty());

    // Act & Assert
    webTestClient
        .delete()
        .uri("/api/sucursal/1/product/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .isEqualTo("Producto eliminado exitosamente");
  }

  @Test
  void testUpdateProductStock_ShouldReturnSuccess() {
    // Arrange
    Product updatedProduct =
        Product.builder().sucursalId(1).name("Test Product").stock(150).build();
    when(productUseCase.updateStock(anyInt(), anyInt())).thenReturn(Mono.just(updatedProduct));

    // Act & Assert
    webTestClient
        .put()
        .uri("/api/product/1/stock")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of("stock", 150))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .value(
            response -> {
              assertEquals("Test Product", response.getName());
              assertEquals(1, response.getSucursalId());
              assertEquals(150, response.getStock());
            });
  }

  @Test
  void testGetProductsWithMaxStockByFranchise_ShouldReturnSuccess() {
    // Arrange
    List<Product> products =
        List.of(
            Product.builder().sucursalId(1).name("Product 1").stock(200).build(),
            Product.builder().sucursalId(2).name("Product 2").stock(200).build());
    when(productUseCase.findProductsWithMaxStockByFranchise(anyInt()))
        .thenReturn(Flux.fromIterable(products));

    // Act & Assert
    webTestClient
        .get()
        .uri("/api/franchise/1/products/max-stock")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Product.class)
        .hasSize(2);
  }

  @Test
  void testUpdateFranchiseName_ShouldReturnSuccess() {
    // Arrange
    Franchise updatedFranchise = Franchise.builder().name("Updated Franchise").build();
    when(franchiseUseCase.updateName(anyInt(), anyString()))
        .thenReturn(Mono.just(updatedFranchise));

    // Act & Assert
    webTestClient
        .put()
        .uri("/api/franchise/1/name")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of("name", "Updated Franchise"))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Franchise.class)
        .value(response -> assertEquals("Updated Franchise", response.getName()));
  }

  @Test
  void testUpdateSucursalName_ShouldReturnSuccess() {
    // Arrange
    Sucursal updatedSucursal = Sucursal.builder().franchiseId(1).name("Updated Sucursal").build();
    when(sucursalUseCase.updateName(anyInt(), anyString())).thenReturn(Mono.just(updatedSucursal));

    // Act & Assert
    webTestClient
        .put()
        .uri("/api/sucursal/1/name")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of("name", "Updated Sucursal"))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Sucursal.class)
        .value(
            response -> {
              assertEquals("Updated Sucursal", response.getName());
              assertEquals(1, response.getFranchiseId());
            });
  }

  @Test
  void testUpdateProductName_ShouldReturnSuccess() {
    // Arrange
    Product updatedProduct =
        Product.builder().sucursalId(1).name("Updated Product").stock(100).build();
    when(productUseCase.updateName(anyInt(), anyString())).thenReturn(Mono.just(updatedProduct));

    // Act & Assert
    webTestClient
        .put()
        .uri("/api/product/1/name")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of("name", "Updated Product"))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .value(
            response -> {
              assertEquals("Updated Product", response.getName());
              assertEquals(1, response.getSucursalId());
              assertEquals(100, response.getStock());
            });
  }

  @Test
  void testCreateFranchise_ShouldReturnError_WhenUseCaseFails() {
    // Arrange
    Franchise franchise = Franchise.builder().name("Test Franchise").build();
    when(franchiseUseCase.save(any(Franchise.class)))
        .thenReturn(
            Mono.error(
                new InfrastructureException(
                    InfrastructureError.DB_ERROR, "Error creating franchise")));

    // Act & Assert
    webTestClient
        .post()
        .uri("/api/franchise")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(franchise)
        .exchange()
        .expectStatus()
        .is5xxServerError()
        .expectBody(ErrorResponse.class)
        .value(
            response -> {
              assertEquals("DB_ERROR", response.getCode());
              assertEquals("Technical error with DB", response.getMessage());
            });
  }

  @Test
  void testUpdateProductStock_ShouldReturnError_WhenUseCaseFails() {
    // Arrange
    when(productUseCase.updateStock(anyInt(), anyInt()))
        .thenReturn(
            Mono.error(
                new InfrastructureException(InfrastructureError.DB_ERROR, "Error updating stock")));

    // Act & Assert
    webTestClient
        .put()
        .uri("/api/product/1/stock")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of("stock", 150))
        .exchange()
        .expectStatus()
        .is5xxServerError()
        .expectBody(ErrorResponse.class)
        .value(
            response -> {
              assertEquals("DB_ERROR", response.getCode());
              assertEquals("Technical error with DB", response.getMessage());
            });
  }

  @Test
  void testGetProductsWithMaxStockByFranchise_ShouldReturnError_WhenUseCaseFails() {
    // Arrange
    when(productUseCase.findProductsWithMaxStockByFranchise(anyInt()))
        .thenReturn(
            Flux.error(
                new InfrastructureException(
                    InfrastructureError.DB_ERROR, "Error finding products")));

    // Act & Assert
    webTestClient
        .get()
        .uri("/api/franchise/1/products/max-stock")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .is5xxServerError()
        .expectBody(ErrorResponse.class)
        .value(
            response -> {
              assertEquals("DB_ERROR", response.getCode());
              assertEquals("Technical error with DB", response.getMessage());
            });
  }
}
