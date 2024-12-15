package auth_server.exception;

import lombok.Builder;

public class ActiveOTPException extends RuntimeException {

  @Builder
  public ActiveOTPException(String message) {
    super(message);
  }

}
