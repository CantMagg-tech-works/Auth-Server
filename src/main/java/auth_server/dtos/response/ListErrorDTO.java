package auth_server.dtos.response;

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

  private ErrorDTO error;
  private List<String> errors;
}
