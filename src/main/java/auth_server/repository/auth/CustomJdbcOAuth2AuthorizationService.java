package auth_server.repository.auth;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public class CustomJdbcOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService {

  private final JdbcTemplate jdbcTemplate;

  public CustomJdbcOAuth2AuthorizationService(JdbcTemplate jdbcTemplate,
      RegisteredClientRepository registeredClientRepository) {
    super(jdbcTemplate, registeredClientRepository);
    this.jdbcTemplate = jdbcTemplate;
  }

  public boolean isAuthorizationCodeValid(String authorizationCode) {
    String query = """
            SELECT COUNT(*) > 0
            FROM oauth2_authorization
            WHERE authorization_code_value = ?
              AND authorization_code_expires_at > CURRENT_TIMESTAMP
        """;

    return Optional.ofNullable(jdbcTemplate.queryForObject(query, Boolean.class, authorizationCode))
        .orElse(false);

  }

  public boolean isRefreshTokenValid(String refreshToken) {
    String query = """
            SELECT COUNT(*) > 0
            FROM oauth2_authorization
            WHERE refresh_token_value = ?
              AND refresh_token_expires_at > CURRENT_TIMESTAMP
        """;

    return Optional.ofNullable(jdbcTemplate.queryForObject(query, Boolean.class, refreshToken))
        .orElse(false);
  }

}
