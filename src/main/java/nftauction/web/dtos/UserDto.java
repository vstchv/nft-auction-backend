package nftauction.web.dtos;

import lombok.Data;

@Data
public class UserDto {
  private String username;
  private String firstName;
  private String lastName;
  private String email;
}
