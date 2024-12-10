package auth_server.config.security;

import auth_server.repository.auth.CustomJdbcOAuth2AuthorizationService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Value("${SECRET_KEY}")
  String secretKey;
  @Value("${REDIRECT_URI}")
  String redirectUri;
  @Value("${LOGOUT_URI}")
  String logoutUri;

  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());
    http.exceptionHandling(exceptions -> exceptions
            .defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("http://localhost:3000/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
        )
        .oauth2ResourceServer(resourceServer -> resourceServer
            .jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
      throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/v1/auths/register",
                "/api/v1/auths/exchange-token",
                "/api/v1/auths/exchange-refresh-token",
                "/error",
                "/swagger-ui/**",
                "/v3/api-docs",
                "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(form -> form
            .loginPage("http://localhost:3000/login")
            .loginProcessingUrl("/login")
            .failureUrl("http://localhost:3000/login?error=true")
            .permitAll());

    return http.build();
  }


  @Bean
  public RegisteredClientRepository registeredClientRepository(DataSource dataSource) {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    JdbcRegisteredClientRepository clientRepository = new JdbcRegisteredClientRepository(
        jdbcTemplate);

    if (clientRepository.findByClientId("users-client") == null) {
      RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
          .clientId("users-client")
          .clientSecret(passwordEncoder().encode(secretKey))
          .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
          .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
          .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
          .redirectUri(redirectUri)
          .postLogoutRedirectUri(logoutUri)
          .scope(OidcScopes.OPENID)
          .scope(OidcScopes.PROFILE)
          .scope("offline_access")
          .tokenSettings(TokenSettings.builder()
              .accessTokenTimeToLive(Duration.ofMinutes(10))
              .refreshTokenTimeToLive(Duration.ofDays(1))
              .reuseRefreshTokens(false)
              .build())
          .clientSettings(ClientSettings.builder()
              .requireAuthorizationConsent(false)
              .requireProofKey(true)
              .build())
          .build();

      clientRepository.save(oidcClient);
    }
    return clientRepository;
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public CustomJdbcOAuth2AuthorizationService customJdbcOAuth2AuthorizationService(
      DataSource dataSource,
      RegisteredClientRepository registeredClientRepository) {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    return new CustomJdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
  }

}
