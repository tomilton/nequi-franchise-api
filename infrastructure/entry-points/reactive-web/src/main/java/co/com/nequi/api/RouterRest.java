package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
  @Bean
  public RouterFunction<ServerResponse> routerFunction(Handler handler) {
    return route(POST("/api/franchise"), handler::createFranchise)
        .andRoute(POST("/api/sucursal"), handler::createSucursal)
        .andRoute(POST("/api/product"), handler::createProduct)
        .andRoute(DELETE("/api/sucursal/{sucursalId}/product/{productId}"), handler::deleteProduct)
        .andRoute(PUT("/api/product/{productId}/stock"), handler::updateProductStock);
  }
}
