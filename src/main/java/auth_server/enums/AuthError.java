package auth_server.enums;

import lombok.Getter;

@Getter
public enum AuthError {

  AUTH_ERROR_1("User not found.", "The user with this email was not found."),
  AUTH_ERROR_2("Failed registration.",
      "Error in registration for new user or the email already exists in database."),
  AUTH_ERROR_3("Role not found", "Role error, no role found in database."),
  AUTH_ERROR_4("Validation error.",
      "The request contains validation errors. Review the fields and correct the errors indicated below.");

  private final String useCase;
  private final String description;

  AuthError(String useCase, String description) {
    this.useCase = useCase;
    this.description = description;
  }
}
