package uz.md.leetcode.payload.section;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SectionDTO {

    private Integer id;

    private String title;

    private String url;

    private String description;

    private Byte maxRate;

    private Integer languageId;

    private Long problemCount;

    private Long tryCount;

    private Long solutionCount;

}
