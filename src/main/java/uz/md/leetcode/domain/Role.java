package uz.md.leetcode.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.md.leetcode.domain.abs.AbsIntegerEntity;
import uz.md.leetcode.domain.enums.PermissionEnum;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<PermissionEnum> permissions;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }

}
