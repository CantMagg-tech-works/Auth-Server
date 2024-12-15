package auth_server.controller;

import auth_server.dtos.request.SendOTPRequestDTO;
import auth_server.enums.AuthMessage;
import auth_server.service.imp.EcUserOTPServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
@Validated
public class EcUserOTPController {

  private final EcUserOTPServiceImp ecUserOTPServiceImp;

  @PostMapping("/otp")
  public ResponseEntity<String> resetPasswordRequest(
      @Valid @RequestBody SendOTPRequestDTO resetPasswordRequestDTO) {
    ecUserOTPServiceImp.sendOtpByEmail(resetPasswordRequestDTO.getEmail());

    return ResponseEntity.status(HttpStatus.OK).body(AuthMessage.AUTH_MESSAGES_2.getDescription());
  }
}
