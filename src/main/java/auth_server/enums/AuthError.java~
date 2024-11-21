package auth_server.enums;

import lombok.Getter;

@Getter
public enum AuthError {

  AUTH_ERROR_0001("User not found.", "The user with this email was not found."),
  AUTH_ERROR_0002("Failed registration.",
      "Error in registration for new user or the email already exists in database."),
  AUTH_ERROR_0003("Role not found", "Role error, no role found in database."),
  AUTH_ERROR_0004("Validation error.",
      "The request contains validation errors. Review the fields and correct the errors indicated below."),
  AUTH_ERROR_0005("Token exchange error.","Error when exchanging authorization code for token.");

  private final String useCase;
  private final String description;

  AuthError(String useCase, String description) {
    this.useCase = useCase;
    this.description = description;
  }
}
