package auth_server.exception;

import lombok.Builder;

public class InvalidRefreshTokenException extends RuntimeException {

  @Builder
  public InvalidRefreshTokenException(String message) {
    super(message);
  }

}
