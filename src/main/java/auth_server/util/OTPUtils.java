package auth_server.util;

import auth_server.dtos.internal.DetailsOTPDTO;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class OTPUtils {

  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  private static final Random RANDOM = new Random();

  private OTPUtils() {
  }

  public static DetailsOTPDTO generateOtpDetails(int minutesToExpire) {
    String otp = generateOTP();
    String otpHash = hashOtp(otp);
    Date creationDate = getCreationDate();
    Date expirationDate = getExpirationDate(creationDate, minutesToExpire);

    return new DetailsOTPDTO(otp, otpHash, creationDate, expirationDate);
  }

  private static String generateOTP() {
    return String.valueOf(RANDOM.nextInt(900000) + 100000);
  }

  private static String hashOtp(String otp) {
    return encoder.encode(otp);
  }

  private static Date getCreationDate() {
    return new Date();
  }

  private static Date getExpirationDate(Date creationDate, int minutes) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(creationDate);
    calendar.add(Calendar.MINUTE, minutes);
    return calendar.getTime();
  }
}
