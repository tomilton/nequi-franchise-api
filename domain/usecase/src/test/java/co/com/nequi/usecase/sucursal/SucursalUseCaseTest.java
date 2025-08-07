package co.com.nequi.usecase.sucursal;

import co.com.nequi.model.sucursal.Sucursal;
import co.com.nequi.model.sucursal.gateways.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SucursalUseCaseTest {

  @Mock
  private SucursalRepository sucursalRepository;

  @InjectMocks
  private SucursalUseCase sucursalUseCase;

  private Sucursal testSucursal;

  @BeforeEach
  void setUp() {
    testSucursal = Sucursal.builder()
        .franchiseId(1)
        .name("Test Sucursal")
        .build();
  }

  @Test
  void save_ShouldReturnSavedSucursal() {
    // Arrange
    when(sucursalRepository.save(any(Sucursal.class)))
        .thenReturn(Mono.just(testSucursal));

    // Act & Assert
    StepVerifier.create(sucursalUseCase.save(testSucursal))
        .expectNext(testSucursal)
        .verifyComplete();
  }

  @Test
  void save_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    String errorMessage = "Error saving sucursal";
    when(sucursalRepository.save(any(Sucursal.class)))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(sucursalUseCase.save(testSucursal))
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void updateName_ShouldReturnUpdatedSucursal() {
    // Arrange
    Integer sucursalId = 1;
    String newName = "Updated Sucursal Name";
    Sucursal updatedSucursal = testSucursal.toBuilder().name(newName).build();
    
    when(sucursalRepository.updateName(anyInt(), anyString()))
        .thenReturn(Mono.just(updatedSucursal));

    // Act & Assert
    StepVerifier.create(sucursalUseCase.updateName(sucursalId, newName))
        .expectNext(updatedSucursal)
        .verifyComplete();
  }

  @Test
  void updateName_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    Integer sucursalId = 1;
    String newName = "Updated Sucursal Name";
    String errorMessage = "Error updating sucursal name";
    when(sucursalRepository.updateName(anyInt(), anyString()))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(sucursalUseCase.updateName(sucursalId, newName))
        .expectError(RuntimeException.class)
        .verify();
  }
} 