package uz.md.leetcode.payload.userProblem;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProblemCDTO {

    private Integer userId;
    private Integer problemId;
    private String solution;

}
