package auth_server.exception;

import lombok.Builder;

public class RefreshTokenExchangeException extends RuntimeException {

  @Builder
  public RefreshTokenExchangeException(String message) {
    super(message);
  }
}
