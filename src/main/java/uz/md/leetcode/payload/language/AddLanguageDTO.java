package uz.md.leetcode.payload.language;

import lombok.*;
import uz.md.leetcode.domain.Language;
import uz.md.leetcode.utils.BaseUtils;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddLanguageDTO {

    @NotBlank(message = "Title must be not blank")
    private String title;

    public Language get() {
        return new Language(title);
    }

    public Language get(Integer id) {
        return new Language(title, BaseUtils.makeUrl(title),id);
    }

}
