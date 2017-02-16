package com.possessor.mapper;

import com.possessor.dto.DtoUser;
import com.possessor.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by rpiotrowicz on 2017-02-07.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({@Mapping(source = "username", target = "account.username"),
    @Mapping(source = "password", target = "account.password")})
    User userDtoToUser(DtoUser DtoUser);

    @Mappings({
            @Mapping(source = "account.username", target = "username"),
            @Mapping(target = "password", ignore = true)})
    DtoUser userToDtoUser(User user);
}
