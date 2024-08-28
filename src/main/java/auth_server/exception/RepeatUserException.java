package auth_server.exception;

import lombok.Builder;

public class RepeatUserException extends RuntimeException {

  @Builder
  public RepeatUserException(String message) {
    super(message);
  }
}
