package auth_server.handlers;

import api_commons.response.ErrorCommons;
import auth_server.dtos.response.ListErrorDTO;
import auth_server.enums.AuthError;
import auth_server.exception.IdRoleNotFoundException;
import auth_server.exception.InvalidCodeException;
import auth_server.exception.InvalidRefreshTokenException;
import auth_server.exception.RepeatUserException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandlers {

  @ExceptionHandler(IdRoleNotFoundException.class)
  public ResponseEntity<ErrorCommons> idRoleNotFoundHandler(IdRoleNotFoundException e) {
    log.error(AuthError.AUTH_ERROR_0003.getDescription(), e);

    ErrorCommons error = ErrorCommons.builder()
        .code(AuthError.AUTH_ERROR_0003.name())
        .message(AuthError.AUTH_ERROR_0003.getDescription())
        .status(HttpStatus.NOT_FOUND.value())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

  }

  @ExceptionHandler(RepeatUserException.class)
  public ResponseEntity<ErrorCommons> repeatUserExceptionHandler(RepeatUserException e) {
    log.error(AuthError.AUTH_ERROR_0002.getDescription(), e);

    ErrorCommons error = ErrorCommons.builder()
        .code(AuthError.AUTH_ERROR_0002.name())
        .message(AuthError.AUTH_ERROR_0002.getDescription())
        .status(HttpStatus.BAD_REQUEST.value())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
    log.error(AuthError.AUTH_ERROR_0001.getDescription(), e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ListErrorDTO> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException e) {
    List<String> errors = e.getBindingResult().getFieldErrors().stream().map(
        DefaultMessageSourceResolvable::getDefaultMessage).toList();

    log.error(AuthError.AUTH_ERROR_0004.getDescription(), e);
    ErrorCommons error = ErrorCommons.builder()
        .code(AuthError.AUTH_ERROR_0004.name())
        .message(AuthError.AUTH_ERROR_0004.getDescription())
        .status(HttpStatus.BAD_REQUEST.value())
        .build();

    ListErrorDTO listError = ListErrorDTO.builder()
        .error(error)
        .errors(errors)
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listError);

  }

  @ExceptionHandler(InvalidCodeException.class)
  public ResponseEntity<ErrorCommons> invalidCodeExceptionHandler(InvalidCodeException e) {
    log.error(AuthError.AUTH_ERROR_0005.getDescription(), e);
    ErrorCommons error = ErrorCommons.builder()
        .code(AuthError.AUTH_ERROR_0005.name())
        .message(AuthError.AUTH_ERROR_0005.getDescription())
        .status(HttpStatus.BAD_REQUEST.value())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(InvalidRefreshTokenException.class)
  public ResponseEntity<ErrorCommons> invalidRefreshTokenExceptionHandler(InvalidRefreshTokenException e) {
    log.error(AuthError.AUTH_ERROR_0006.getDescription(), e);
    ErrorCommons error = ErrorCommons.builder()
        .code(AuthError.AUTH_ERROR_0006.name())
        .message(AuthError.AUTH_ERROR_0006.getDescription())
        .status(HttpStatus.BAD_REQUEST.value())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

}
