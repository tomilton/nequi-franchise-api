package co.com.nequi.model.enums;

import co.com.nequi.model.exception.InfrastructureException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum InfrastructureError {
  DB_ERROR("Technical error with DB"),
  UNEXPECTED_EXCEPTION("Error inesperado");

  private final String message;

  InfrastructureError(String message) {
    this.message = message;
  }

  public InfrastructureException build(Throwable cause) {
    return new InfrastructureException(this, cause);
  }

  public InfrastructureException build(String message) {
    return new InfrastructureException(this, message);
  }
}
