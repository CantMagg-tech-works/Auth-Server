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
public class ResponseFeign implements Serializable {

  private String id;
  private String username;
  private Integer role;
  private Boolean valid;
}
