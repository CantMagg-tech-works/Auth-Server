package auth_server.exception;

import lombok.Builder;

public class EmailNotFoundException extends RuntimeException {

  @Builder
  public EmailNotFoundException(String message) {
    super(message);
  }

}
