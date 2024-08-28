package auth_server.exception;

import lombok.Builder;

public class IdRoleNotFoundException extends RuntimeException {

  @Builder
  public IdRoleNotFoundException(String message) {
    super(message);
  }
}
