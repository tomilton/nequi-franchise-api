package co.com.nequi.model.exception;

import co.com.nequi.model.enums.BusinessErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends Exception {
  private final BusinessErrorMessage businessErrorMessage;
}
