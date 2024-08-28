package auth_server.dtos.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseToken implements Serializable {

  private String accessToken;
  private Long accessTokenExpireTime;
  private String refreshToken;
  private Long refreshTokenExpireTime;
}
