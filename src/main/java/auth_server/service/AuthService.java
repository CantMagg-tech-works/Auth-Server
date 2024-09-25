package auth_server.service;

import auth_server.dtos.request.RequestRegister;

public interface AuthService {

  String register(RequestRegister request);


}
