package co.com.nequi.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {
  FRANCHISE_UPDATE_NOT_FOUND("Error al actualizar franquicia, no se encontró el registro");

  private final String message;
}
