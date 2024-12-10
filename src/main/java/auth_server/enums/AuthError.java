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
  AUTH_ERROR_0005("Token exchange error.", "Error when exchanging authorization code for token: the code has already been used or has expired."),
  AUTH_ERROR_0006("Refresh Token exchange error.",
      "Error when exchanging refresh token for token."),
  AUTH_ERROR_0007("Invalid Authorization Code", "The authorization code is not valid or expired."),
  AUTH_ERROR_0008("Invalid Refresh Token", "The refresh token is not valid or expired.");

  private final String useCase;
  private final String description;

  AuthError(String useCase, String description) {
    this.useCase = useCase;
    this.description = description;
  }
}
