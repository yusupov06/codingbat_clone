package uz.md.leetcode.payload.problem;

import lombok.*;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class ProblemCDTO {

    private String title;
    private String description;
    private String methodSignature;
    private Integer sectionId;

}
