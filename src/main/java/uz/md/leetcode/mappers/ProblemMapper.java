package uz.md.leetcode.mappers;

import org.mapstruct.Mapper;
import uz.md.leetcode.domain.Problem;
import uz.md.leetcode.payload.problem.ProblemCDTO;
import uz.md.leetcode.payload.problem.ProblemDTO;
import uz.md.leetcode.payload.problem.ProblemUDTO;



@Mapper(componentModel = "spring")
public interface ProblemMapper extends EntityMapper<
        Problem,
        ProblemDTO,
        ProblemCDTO,
        ProblemUDTO
        > {
}
