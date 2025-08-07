package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
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
class FranchiseUseCaseTest {

  @Mock
  private FranchiseRepository franchiseRepository;

  @InjectMocks
  private FranchiseUseCase franchiseUseCase;

  private Franchise testFranchise;

  @BeforeEach
  void setUp() {
    testFranchise = Franchise.builder()
        .name("Test Franchise")
        .build();
  }

  @Test
  void save_ShouldReturnSavedFranchise() {
    // Arrange
    when(franchiseRepository.save(any(Franchise.class)))
        .thenReturn(Mono.just(testFranchise));

    // Act & Assert
    StepVerifier.create(franchiseUseCase.save(testFranchise))
        .expectNext(testFranchise)
        .verifyComplete();
  }

  @Test
  void save_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    String errorMessage = "Error saving franchise";
    when(franchiseRepository.save(any(Franchise.class)))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(franchiseUseCase.save(testFranchise))
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void updateName_ShouldReturnUpdatedFranchise() {
    // Arrange
    Integer franchiseId = 1;
    String newName = "Updated Franchise Name";
    Franchise updatedFranchise = testFranchise.toBuilder().name(newName).build();
    
    when(franchiseRepository.findById(anyInt()))
        .thenReturn(Mono.just(testFranchise));
    when(franchiseRepository.updateName(anyInt(), anyString()))
        .thenReturn(Mono.just(updatedFranchise));

    // Act & Assert
    StepVerifier.create(franchiseUseCase.updateName(franchiseId, newName))
        .expectNext(updatedFranchise)
        .verifyComplete();
  }

  @Test
  void updateName_ShouldReturnError_WhenRepositoryFails() {
    // Arrange
    Integer franchiseId = 1;
    String newName = "Updated Franchise Name";
    String errorMessage = "Error updating franchise name";
    when(franchiseRepository.findById(anyInt()))
        .thenReturn(Mono.just(testFranchise));
    when(franchiseRepository.updateName(anyInt(), anyString()))
        .thenReturn(Mono.error(new RuntimeException(errorMessage)));

    // Act & Assert
    StepVerifier.create(franchiseUseCase.updateName(franchiseId, newName))
        .expectError(RuntimeException.class)
        .verify();
  }
} 