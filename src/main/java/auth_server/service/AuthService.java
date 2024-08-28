package auth_server.service;

import auth_server.dtos.request.RequestLogin;
import auth_server.dtos.response.ResponseToken;

public interface AuthService {

  String register(RequestLogin request);


}
