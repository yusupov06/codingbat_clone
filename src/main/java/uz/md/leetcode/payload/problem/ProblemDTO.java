package uz.md.leetcode.payload.problem;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProblemDTO {

    private Integer id;
    private String title;
    private String description;
    private String methodSignature;

}
