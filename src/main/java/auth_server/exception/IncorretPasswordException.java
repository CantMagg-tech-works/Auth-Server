package auth_server.exception;

import lombok.Builder;

public class IncorretPasswordException extends RuntimeException {

  @Builder
  public IncorretPasswordException(String message) {
    super(message);
  }
}
