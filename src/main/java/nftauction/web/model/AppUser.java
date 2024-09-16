package nftauction.web.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nftauction.web.enums.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class AppUser {
  @Id
  @GeneratedValue
  private Integer id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private boolean verifiedProfile;
  private boolean enabled;
  @Enumerated(EnumType.STRING)
  private Role role;

}