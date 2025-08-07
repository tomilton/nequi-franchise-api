package co.com.nequi.model.exception;


import co.com.nequi.model.enums.InfrastructureError;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
    public class InfrastructureException extends RuntimeException {

    private final InfrastructureError infrastructureError;

    public InfrastructureException(InfrastructureError infrastructureError) {
        this.infrastructureError = infrastructureError;
    }

    public InfrastructureException(InfrastructureError infrastructureError, Throwable cause) {
        super(cause);
        this.infrastructureError = infrastructureError;
    }

    public InfrastructureException(InfrastructureError infrastructureError, String message) {
        super(message);
        this.infrastructureError = infrastructureError;
    }
}
