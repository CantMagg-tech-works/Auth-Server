package auth_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_role", schema = "auth_server")
public class UserRoleModel {

  @Id
  @Column(name = "user_role_id")
  private Integer userRoleId;
  @Column(name = "role_description")
  private String roleDescription;
}
