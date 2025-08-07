package co.com.nequi.usecase.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductUseCase productUseCase;

  private Product testProduct;

  @BeforeEach
  void setUp() {
    testProduct = Product.builder()
        .sucursalId(1)
        .name("Test Product")
        .stock(100)
        .build();
  }

  @Test
  void save_ShouldReturnSavedProduct() {
    // Arrange
    when(productRepository.save(any(Product.class)))
        .thenReturn(Mono.just(testProduct));

    // Act & Assert
    StepVerifier.create(productUseCase.save(testProduct))
        .expectNext(testProduct)
        .verifyComplete();
  }

  @Test
  void save_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    String errorMessage = "Error saving product";
    when(productRepository.save(any(Product.class)))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(productUseCase.save(testProduct))
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void delete_ShouldCompleteSuccessfully() {
    // Arrange
    Integer productId = 1;
    Integer sucursalId = 1;
    when(productRepository.delete(anyInt(), anyInt()))
        .thenReturn(Mono.empty());

    // Act & Assert
    StepVerifier.create(productUseCase.delete(productId, sucursalId))
        .verifyComplete();
  }

  @Test
  void delete_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    Integer productId = 1;
    Integer sucursalId = 1;
    String errorMessage = "Error deleting product";
    when(productRepository.delete(anyInt(), anyInt()))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(productUseCase.delete(productId, sucursalId))
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void updateStock_ShouldReturnUpdatedProduct() {
    // Arrange
    Integer productId = 1;
    Integer newStock = 150;
    Product updatedProduct = testProduct.toBuilder().stock(newStock).build();
    
    when(productRepository.updateStock(anyInt(), anyInt()))
        .thenReturn(Mono.just(updatedProduct));

    // Act & Assert
    StepVerifier.create(productUseCase.updateStock(productId, newStock))
        .expectNext(updatedProduct)
        .verifyComplete();
  }

  @Test
  void updateStock_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    Integer productId = 1;
    Integer newStock = 150;
    String errorMessage = "Error updating stock";
    when(productRepository.updateStock(anyInt(), anyInt()))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(productUseCase.updateStock(productId, newStock))
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void findProductsWithMaxStockByFranchise_ShouldReturnProductsList() {
    // Arrange
    Integer franchiseId = 1;
    List<Product> expectedProducts = List.of(
        Product.builder().sucursalId(1).name("Product 1").stock(200).build(),
        Product.builder().sucursalId(2).name("Product 2").stock(200).build()
    );
    
    when(productRepository.findProductsWithMaxStockByFranchise(anyInt()))
        .thenReturn(Flux.fromIterable(expectedProducts));

    // Act & Assert
    StepVerifier.create(productUseCase.findProductsWithMaxStockByFranchise(franchiseId))
        .expectNext(expectedProducts.get(0))
        .expectNext(expectedProducts.get(1))
        .verifyComplete();
  }

  @Test
  void findProductsWithMaxStockByFranchise_ShouldReturnEmpty_WhenNoProductsFound() {
    // Arrange
    Integer franchiseId = 1;
    when(productRepository.findProductsWithMaxStockByFranchise(anyInt()))
        .thenReturn(Flux.empty());

    // Act & Assert
    StepVerifier.create(productUseCase.findProductsWithMaxStockByFranchise(franchiseId))
        .verifyComplete();
  }

  @Test
  void findProductsWithMaxStockByFranchise_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    Integer franchiseId = 1;
    String errorMessage = "Error finding products";
    when(productRepository.findProductsWithMaxStockByFranchise(anyInt()))
        .thenReturn(Flux.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(productUseCase.findProductsWithMaxStockByFranchise(franchiseId))
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void updateName_ShouldReturnUpdatedProduct() {
    // Arrange
    Integer productId = 1;
    String newName = "Updated Product Name";
    Product updatedProduct = testProduct.toBuilder().name(newName).build();
    
    when(productRepository.updateName(anyInt(), anyString()))
        .thenReturn(Mono.just(updatedProduct));

    // Act & Assert
    StepVerifier.create(productUseCase.updateName(productId, newName))
        .expectNext(updatedProduct)
        .verifyComplete();
  }

  @Test
  void updateName_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    Integer productId = 1;
    String newName = "Updated Product Name";
    String errorMessage = "Error updating product name";
    when(productRepository.updateName(anyInt(), anyString()))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(productUseCase.updateName(productId, newName))
        .expectError(RuntimeException.class)
        .verify();
  }
} 