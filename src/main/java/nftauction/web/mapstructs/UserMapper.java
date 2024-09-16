package nftauction.web.mapstructs;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import nftauction.web.dtos.UserDto;
import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.model.AppUser;


@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  AppUser mapToUser(UserRegisterDto userRegisterDto);

  UserDto mapToUserDto(AppUser user);

  List<UserDto> mapToUserDtoList(List<AppUser> users);
}
