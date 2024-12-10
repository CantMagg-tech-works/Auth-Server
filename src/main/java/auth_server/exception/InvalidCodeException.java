package auth_server.exception;

import lombok.Builder;

public class InvalidCodeException extends RuntimeException {

  @Builder
  public InvalidCodeException(String message) {
    super(message);
  }

}
