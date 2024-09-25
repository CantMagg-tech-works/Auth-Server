package auth_server.repository;

import auth_server.model.EcUserModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface EcUserRepository extends JpaRepository<EcUserModel, UUID> {

  Boolean existsByUsername(String email);

  @Query("SELECT u FROM EcUserModel u WHERE u.username = :email")
  Optional<EcUserModel> findByEmail(@Param("email") String email);

  Optional<EcUserModel> findByUsername(String username);
}
