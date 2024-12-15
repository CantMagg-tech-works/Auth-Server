package auth_server.service.imp;

import auth_server.dtos.request.RequestRegister;
import auth_server.dtos.response.RefreshTokenResponse;
import auth_server.dtos.response.TokenResponse;
import auth_server.enums.AuthError;
import auth_server.enums.AuthMessage;
import auth_server.exception.IdRoleNotFoundException;
import auth_server.exception.InvalidCodeException;
import auth_server.exception.InvalidRefreshTokenException;
import auth_server.exception.RepeatUserException;
import auth_server.infrastructure.EventPublisher;
import auth_server.model.EcUserModel;
import auth_server.model.UserRoleModel;
import auth_server.repository.EcUserRepository;
import auth_server.repository.UserRoleRepository;
import auth_server.repository.auth.CustomJdbcOAuth2AuthorizationService;
import auth_server.service.AuthService;
import auth_server.util.TokenUtil;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final EcUserRepository ecUserRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenUtil tokenUtil;
  private final CustomJdbcOAuth2AuthorizationService customJdbcOAuth2AuthorizationService;
  private final EventPublisher eventPublisher;


  @Override
  public String register(RequestRegister request) {
    if (Boolean.TRUE.equals(ecUserRepository.existsByUsername(request.getEmail()))) {
      throw RepeatUserException
          .builder()
          .message(AuthError.AUTH_ERROR_0002.getDescription())
          .build();
    }
    UserRoleModel userRoleModel = userRoleRepository.findByRoleDescription("ROLE_USER").orElseThrow(
        () -> IdRoleNotFoundException
            .builder()
            .message(AuthError.AUTH_ERROR_0003.getDescription())
            .build());

    EcUserModel userModel = EcUserModel
        .builder()
        .username(request.getEmail().trim())
        .password(passwordEncoder.encode(request.getPassword()))
        .userRole(userRoleModel)
        .creationDate(new Date())
        .build();
    ecUserRepository.save(userModel);

    return AuthMessage.AUTH_MESSAGES_1.getDescription();
  }

  @Override
  public TokenResponse exchangeToken(String code, String codeVerifier) {
    if (!customJdbcOAuth2AuthorizationService.isAuthorizationCodeValid(code)) {
      throw new InvalidCodeException(AuthError.AUTH_ERROR_0007.getDescription());
    }
    return tokenUtil.exchangeAuthorizationCodeForToken(code,
        codeVerifier);
  }

  @Override
  public RefreshTokenResponse refreshToken(String refreshToken) {
    if (!customJdbcOAuth2AuthorizationService.isRefreshTokenValid(refreshToken)) {
      throw new InvalidRefreshTokenException(AuthError.AUTH_ERROR_0008.getDescription());
    }
    return tokenUtil.exchangeRefreshTokenForAccessToken(refreshToken);
  }

}
