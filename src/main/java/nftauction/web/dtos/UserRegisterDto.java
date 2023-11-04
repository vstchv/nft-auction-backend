package nftauction.web.dtos;

import lombok.Data;

@Data
public class UserRegisterDto {
  String username;
  String password;
  String firstName;
  String lastName;
  String email;
}
