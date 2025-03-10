package auth_server.dtos.integration;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailOTPDTO implements Serializable {

  private String email;
  private String otp;
  private String subject;
  private String templateName;
  private String id;
}
