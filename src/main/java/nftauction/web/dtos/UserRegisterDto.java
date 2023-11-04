package nftauction.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
}
