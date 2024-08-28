package auth_server.exception;

import lombok.Builder;

public class UserIdNotFoundException extends RuntimeException {

  @Builder
  public UserIdNotFoundException(String message) {
    super(message);
  }

}
