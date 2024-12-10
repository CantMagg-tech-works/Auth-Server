package auth_server.dtos.response;

import api_commons.response.ErrorCommons;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListErrorDTO implements Serializable {

  private ErrorCommons error;
  private List<String> errors;
}
