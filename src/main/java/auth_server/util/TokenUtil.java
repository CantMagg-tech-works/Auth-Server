package auth_server.util;

import auth_server.dtos.response.RefreshTokenResponse;
import auth_server.dtos.response.TokenResponse;
import auth_server.enums.AuthError;
import auth_server.exception.TokenExchangeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtil {

  @Value("${CLIENT_ID}")
  private String clientId;

  @Value("${TOKEN_URI}")
  private String tokenUri;

  @Value("${REDIRECT_URI}")
  private String redirectUri;

  @Value("${SECRET_KEY}")
  private String clientSecret;

  private final RestTemplate restTemplate;


  public TokenResponse exchangeAuthorizationCodeForToken(String code, String codeVerifier) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("code", code);
    params.add("redirect_uri", redirectUri);
    params.add("code_verifier", codeVerifier);

    return sendTokenRequest(params, TokenResponse.class, AuthError.AUTH_ERROR_0005);
  }


  public RefreshTokenResponse exchangeRefreshTokenForAccessToken(String refreshToken) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "refresh_token");
    params.add("refresh_token", refreshToken);

    return sendTokenRequest(params, RefreshTokenResponse.class, AuthError.AUTH_ERROR_0006);
  }

  private <T> T sendTokenRequest(MultiValueMap<String, String> params, Class<T> responseType, AuthError error) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(clientId, clientSecret);

    try {
      HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
      ResponseEntity<T> response = restTemplate.exchange(
          tokenUri,
          HttpMethod.POST,
          requestEntity,
          responseType
      );
      return response.getBody();
    } catch (HttpStatusCodeException e) {
      log.error(error.getUseCase(), e.getStatusCode(), e.getResponseBodyAsString());
      throw new TokenExchangeException(error.getDescription());
    }
  }
}
