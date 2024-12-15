package auth_server.repository;

import auth_server.model.EcUserOTPModel;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcUserOTPRepository extends JpaRepository<EcUserOTPModel, Long> {

  boolean existsByUserIdAndExpirationDateAfter(Long userId, Date currentDate);
}
