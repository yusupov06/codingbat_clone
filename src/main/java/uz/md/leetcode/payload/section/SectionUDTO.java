package uz.md.leetcode.payload.section;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class SectionUDTO {

    private Integer id;

    private String title;

    private String url;

    private String description;

    private Byte maxRate;

    private Integer languageId;

}
