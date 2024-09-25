package auth_server.controller;

import auth_server.dtos.request.RequestRegister;
import auth_server.service.imp.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
  public String register(@Valid @RequestBody RequestRegister request) {
    return authServiceImp.register(request);
  }

}
