package auth_server.enums;

import lombok.Getter;

@Getter
public enum AuthError {
  AUTH_ERROR_1("login failed", "Email or password incorrect"),
  AUTH_ERROR_2("register failed", "Email already exists"),
  AUTH_ERROR_3("role not found", "Role not found"),
  AUTH_ERROR_4("user not found", "User not found");

  private final String useCase;
  private final String description;

  AuthError(String message, String description) {
    this.useCase = message;
    this.description = description;
  }
}
