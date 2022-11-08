package uz.md.leetcode.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.md.leetcode.payload.DTO;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUDTO implements DTO {

    private String email;
    private String password;

}
