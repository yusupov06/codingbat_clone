package uz.md.leetcode.mappers;

import org.mapstruct.Mapper;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.payload.auth.UserUDTO;


@Mapper(componentModel = "spring")
public interface UserMapper extends MapperMarker, EntityMapper<
        User,
        UserDTO,
        UserRegisterDTO,
        UserUDTO
        > {

}
