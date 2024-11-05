package auth_server.service;

import auth_server.dtos.request.RequestRegister;
import auth_server.dtos.response.TokenResponse;

public interface AuthService {

  String register(RequestRegister request);

  TokenResponse exchangeToken(String code, String codeVerifier);

}
