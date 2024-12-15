package auth_server.mapper;

import auth_server.dtos.integration.EmailOTPDTO;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

  default Map<String, String> emailOtpDTOToMap(EmailOTPDTO dto) {
    Map<String, String> map = new HashMap<>();
    map.put("email", dto.getEmail());
    map.put("otp", dto.getOtp());
    map.put("subject", dto.getSubject());
    map.put("templateName", dto.getTemplateName());
    map.put("id", dto.getId());
    return map;
  }
}
