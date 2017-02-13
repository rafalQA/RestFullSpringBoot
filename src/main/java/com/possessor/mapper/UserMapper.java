package com.possessor.mapper;

import com.possessor.dto.DtoUser;
import com.possessor.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by rpiotrowicz on 2017-02-07.
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "username", target = "account.username")
    User userDtoToUser(DtoUser dtoUser);

    @Mapping(source = "account.username", target = "username")
    DtoUser userToDtoUser(User user);
}
