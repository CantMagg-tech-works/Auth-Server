package auth_server.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegister implements Serializable {

  @NotBlank(message = "Enter an email to register.")
  @Pattern(
      regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
      message = "Invalid email format.")
  private String email;

  @NotBlank(message = "Enter a password to register.")
  @Size(
      min = 8,
      max = 20,
      message = "Password length should be between 8 and 20 characters.")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
      message = "The password must contain at least one uppercase letter, one lowercase letter, one digit, special character, and must not contain spaces.")
  private String password;
}
