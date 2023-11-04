package nftauction.web.mapstructs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.model.User;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "verifiedProfile", ignore = true)
  User mapToUser(UserRegisterDto userRegisterDto);


}
