package auth_server.enums;

import lombok.Getter;

@Getter
public enum AuthLogMessage {

  EVENT_PUBLISHED("Event successfully published."),
  EVENT_ERROR("Error while publishing the event."),
  EVENT_SENDING("Sending event of type: {}"),
  EVENT_PAYLOAD("Event payload: {}");

  private final String message;

  AuthLogMessage(String message) {
    this.message = message;
  }

}
