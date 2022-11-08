package uz.md.leetcode.payload.userProblem;

import lombok.*;
import uz.md.leetcode.payload.problem.ProblemDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProblemDTO {

    private Long id;

    private Integer userId;

    private ProblemDTO problem;

    private String solution;

    private Boolean solved;

}
