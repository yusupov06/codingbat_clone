package uz.md.leetcode.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.md.leetcode.domain.UserProblem;
import uz.md.leetcode.payload.userProblem.UserProblemCDTO;
import uz.md.leetcode.payload.userProblem.UserProblemDTO;
import uz.md.leetcode.payload.userProblem.UserProblemUDTO;

import java.util.List;



@Mapper(componentModel = "spring")
public interface UserProblemMapper extends EntityMapper<
        UserProblem,
        UserProblemDTO,
        UserProblemCDTO,
        UserProblemUDTO
        > {

    @Override
    @Mapping(target = "userId", expression = "java(getUserId(userProblem))")
    UserProblemDTO toDTO(UserProblem userProblem);

    default Integer getUserId(UserProblem userProblem){
        return userProblem.getUser().getId();
    }
    @Override
    List<UserProblemDTO> toDTO(List<UserProblem> list);
}
