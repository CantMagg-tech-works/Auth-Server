package auth_server.repository;

import auth_server.model.UserRoleModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {

  @Query("SELECT u FROM UserRoleModel u WHERE u.roleDescription = :role")
  Optional<UserRoleModel> findByRoleDescription(@Param("role") String role);

}
