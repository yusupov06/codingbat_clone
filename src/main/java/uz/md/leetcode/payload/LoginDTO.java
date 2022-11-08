package uz.md.leetcode.payload;;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginDTO {
    private String email;
    private String password;
}
