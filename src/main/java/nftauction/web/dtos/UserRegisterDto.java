package nftauction.web.dtos;

import lombok.Data;
import nftauction.web.enums.Role;

@Data
public class UserRegisterDto {
  String username;
  String password;
  String firstName;
  String lastName;
  String email;
  Role role;
}
