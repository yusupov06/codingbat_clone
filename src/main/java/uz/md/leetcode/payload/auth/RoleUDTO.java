package uz.md.leetcode.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.md.leetcode.domain.enums.PermissionEnum;

import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RoleUDTO {
    private Integer id;
    private String name;
    private String description;
    private Set<PermissionEnum> permissions;
}
