package uz.md.leetcode.payload.auth;

import lombok.*;
import uz.md.leetcode.domain.enums.PermissionEnum;

import java.util.Set;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {

    private Integer id;

    private String name;

    private String description;

    private Set<PermissionEnum> permissions;

}
