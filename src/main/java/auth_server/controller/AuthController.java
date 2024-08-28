package auth_server.controller;

import auth_server.dtos.request.RequestLogin;
import auth_server.service.imp.AuthServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

  private final AuthServiceImplement authServiceImp;

  @PostMapping("/register")
  public String register(@RequestBody RequestLogin request) {
    return authServiceImp.register(request);
  }

}
