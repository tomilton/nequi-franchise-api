package co.com.nequi.api;

import co.com.nequi.model.exception.BusinessException;
import co.com.nequi.model.exception.InfrastructureException;
import co.com.nequi.model.exception.ErrorResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static co.com.nequi.model.enums.InfrastructureError.UNEXPECTED_EXCEPTION;

@Component
@Order(-2)
public class ErrorHandler extends AbstractErrorWebExceptionHandler {

  HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

  public ErrorHandler(
      DefaultErrorAttributes errorAttributes,
      ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer) {
    super(errorAttributes, new WebProperties.Resources(), applicationContext);
    this.setMessageWriters(serverCodecConfigurer.getWriters());
    this.setMessageReaders(serverCodecConfigurer.getReaders());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(
      final ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
    return Mono.just(request)
        .map(this::getError)
        .flatMap(Mono::error)
        .onErrorResume(InfrastructureException.class, this::buildErrorResponse)
        .onErrorResume(BusinessException.class, this::buildErrorResponse)
        .onErrorResume(this::buildErrorResponse)
        .cast(ErrorResponse.class)
        .flatMap(this::buildResponse);
  }

  private Mono<ErrorResponse> buildErrorResponse(InfrastructureException infrastructureException) {
    return Mono.just(
        ErrorResponse.builder()
            .code(infrastructureException.getInfrastructureError().name())
            .message(infrastructureException.getInfrastructureError().getMessage())
            .build());
  }

  private Mono<ErrorResponse> buildErrorResponse(BusinessException businessException) {
    status = HttpStatus.BAD_REQUEST;
    return Mono.just(
        ErrorResponse.builder()
            .code(businessException.getBusinessErrorMessage().name())
            .message(businessException.getBusinessErrorMessage().getMessage())
            .build());
  }

  private Mono<ErrorResponse> buildErrorResponse(Throwable throwable) {
    return Mono.just(
        ErrorResponse.builder()
            .code(UNEXPECTED_EXCEPTION.name())
            .message(UNEXPECTED_EXCEPTION.getMessage())
            .build());
  }

  private Mono<ServerResponse> buildResponse(ErrorResponse errorResponse) {
    return ServerResponse.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(errorResponse);
  }
}
