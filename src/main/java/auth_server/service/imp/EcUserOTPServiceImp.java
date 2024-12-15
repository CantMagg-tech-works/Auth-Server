package auth_server.service.imp;

import auth_server.dtos.internal.DetailsOTPDTO;
import auth_server.dtos.integration.EmailOTPDTO;
import auth_server.enums.AuthError;
import auth_server.exception.ActiveOTPException;
import auth_server.infrastructure.EventPublisher;
import auth_server.mapper.AuthMapper;
import auth_server.model.EcUserModel;
import auth_server.model.EcUserOTPModel;
import auth_server.repository.EcUserOTPRepository;
import auth_server.repository.EcUserRepository;
import auth_server.service.EcUserOTPService;
import auth_server.util.OTPUtils;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EcUserOTPServiceImp implements EcUserOTPService {

  private final EcUserRepository ecUserRepository;
  private final EcUserOTPRepository ecUserOTPRepository;
  private final EventPublisher eventPublisher;
  private final AuthMapper authMapper;

  @Override
  public void sendOtpByEmail(String email) {

    EcUserModel ecUserModel = ecUserRepository.findByEmail(email).orElseThrow(
        () -> new UsernameNotFoundException(AuthError.AUTH_ERROR_0001.getDescription()));

    if (ecUserOTPRepository.existsByUserIdAndExpirationDateAfter(ecUserModel.getUserId(),
        new Date())) {
      throw new ActiveOTPException(AuthError.AUTH_ERROR_0009.getDescription());
    }
    DetailsOTPDTO otpDetails = OTPUtils.generateOtpDetails(1);

    EcUserOTPModel otpModel = EcUserOTPModel.builder()
        .userId(ecUserModel.getUserId())
        .otpHash(otpDetails.getOtpHash())
        .createdAt(otpDetails.getCreationDate())
        .expirationDate(otpDetails.getExpirationDate())
        .build();

    ecUserOTPRepository.save(otpModel);

    EmailOTPDTO emailOTPDTO = EmailOTPDTO
        .builder()
        .email(email)
        .otp(otpDetails.getOtp())
        .subject("Reset password")
        .templateName("Password Recovery Template")
        .id("2")
        .build();

    Map<String, String> payload = authMapper.emailOtpDTOToMap(emailOTPDTO);

    eventPublisher.createEvent(payload, "reset-password");
  }
}
