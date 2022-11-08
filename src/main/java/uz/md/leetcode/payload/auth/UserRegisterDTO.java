package uz.md.leetcode.payload.auth;

import lombok.*;
import uz.md.leetcode.payload.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRegisterDTO implements DTO {

    @NotBlank(message = "{MUST_NOT_BE_BLANK_EMAIL}")
    @Email
    private String email;

    @NotBlank(message = "{MUST_NOT_BE_BLANK_PASSWORD}")
    private String password;

}
