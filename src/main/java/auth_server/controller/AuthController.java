package auth_server.controller;

import auth_server.dtos.request.ExchangeTokenRequestDTO;
import auth_server.dtos.request.RefreshTokenRequestDTO;
import auth_server.dtos.request.RequestRegister;
import auth_server.dtos.response.RefreshTokenResponse;
import auth_server.dtos.response.TokenResponse;
import auth_server.service.imp.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final AuthServiceImpl authServiceImp;


  @PostMapping("/register")
  public String register(@Valid @ModelAttribute RequestRegister request) {
    return authServiceImp.register(request);
  }

  @PostMapping("/exchange-token")
  public ResponseEntity<TokenResponse> exchangeToken(
      @Valid @RequestBody ExchangeTokenRequestDTO exchangeTokenRequestDTO) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authServiceImp.exchangeToken(exchangeTokenRequestDTO.getCode(),
            exchangeTokenRequestDTO.getCodeVerifier()));
  }

  @PostMapping("/exchange-refresh-token")
  public ResponseEntity<RefreshTokenResponse> exchangeRefreshToken(
      @Valid @RequestBody RefreshTokenRequestDTO refreshToken) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authServiceImp.refreshToken(refreshToken.getRefreshToken()));
  }

}
