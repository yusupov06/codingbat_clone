package uz.md.leetcode.payload.section;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder


public class SectionCDTO {


    private String title;

    private String description;

    private Byte maxRate;

    private Integer languageId;

    public SectionCDTO(String title) {
        this.title = title;
    }

    public SectionCDTO(String title, Integer languageId) {
        this.title = title;
        this.languageId = languageId;
    }

}
