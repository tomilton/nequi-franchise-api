package co.com.nequi.config;

import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.product.gateways.ProductRepository;
import co.com.nequi.model.sucursal.gateways.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'UseCase' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public FranchiseRepository franchiseRepository() {
            return new MockFranchiseRepository();
        }

        @Bean
        public SucursalRepository sucursalRepository() {
            return new MockSucursalRepository();
        }

        @Bean
        public ProductRepository productRepository() {
            return new MockProductRepository();
        }
    }

    // Mock implementations for testing
    static class MockFranchiseRepository implements FranchiseRepository {
        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.franchise.Franchise> save(co.com.nequi.model.franchise.Franchise franchise) {
            return reactor.core.publisher.Mono.empty();
        }

        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.franchise.Franchise> updateName(Integer franchiseId, String newName) {
            return reactor.core.publisher.Mono.empty();
        }
    }

    static class MockSucursalRepository implements SucursalRepository {
        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.sucursal.Sucursal> save(co.com.nequi.model.sucursal.Sucursal sucursal) {
            return reactor.core.publisher.Mono.empty();
        }

        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.sucursal.Sucursal> updateName(Integer sucursalId, String newName) {
            return reactor.core.publisher.Mono.empty();
        }
    }

    static class MockProductRepository implements ProductRepository {
        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.product.Product> save(co.com.nequi.model.product.Product product) {
            return reactor.core.publisher.Mono.empty();
        }

        @Override
        public reactor.core.publisher.Mono<Void> delete(Integer productId, Integer sucursalId) {
            return reactor.core.publisher.Mono.empty();
        }

        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.product.Product> updateStock(Integer productId, Integer newStock) {
            return reactor.core.publisher.Mono.empty();
        }

        @Override
        public reactor.core.publisher.Flux<co.com.nequi.model.product.Product> findProductsWithMaxStockByFranchise(Integer franchiseId) {
            return reactor.core.publisher.Flux.empty();
        }

        @Override
        public reactor.core.publisher.Mono<co.com.nequi.model.product.Product> updateName(Integer productId, String newName) {
            return reactor.core.publisher.Mono.empty();
        }
    }
}