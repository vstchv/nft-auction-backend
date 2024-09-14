package nftauction.web.mapstructs;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import nftauction.web.dtos.UserDto;
import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.model.User;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "firstName", target = "firstName")
  @Mapping(source = "lastName", target = "lastName")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "role", target = "role")
  User mapToUser(UserRegisterDto userRegisterDto);

  UserDto mapToUserDto(User user);

  List<UserDto> mapToUserDtoList(List<User> users);


}
