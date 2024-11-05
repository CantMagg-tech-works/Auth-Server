package auth_server.exception;

import lombok.Builder;

public class TokenExchangeException extends RuntimeException {

  @Builder
  public TokenExchangeException(String message) {
    super(message);
  }

}
