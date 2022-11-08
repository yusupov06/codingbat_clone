package uz.md.leetcode.payload.auth;

import lombok.*;
import uz.md.leetcode.domain.Role;
import uz.md.leetcode.payload.DTO;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO implements DTO {

    private Integer id;
    private String email;
    private String password;
    private Role role;
    private UUID uploadAvatarId;
}
