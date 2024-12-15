package auth_server.dtos.internal;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailsOTPDTO implements Serializable {

  private String otp;
  private String otpHash;
  private Date creationDate;
  private Date expirationDate;

}
