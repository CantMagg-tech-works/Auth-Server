package auth_server.enums;

import lombok.Getter;

@Getter
public enum AuthMessage {

  AUTH_MESSAGES_1("user creation", "User created successfully"),
  AUTH_MESSAGES_2("Sending OTP", "OTP sent successfully");

  private final String useCase;
  private final String description;

  AuthMessage(String useCase, String description) {
    this.useCase = useCase;
    this.description = description;
  }
}
