package uz.md.leetcode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.md.leetcode.domain.abs.AbsIntegerEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class User extends AbsIntegerEntity implements UserDetails, EntityMarker {
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    @ManyToOne
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    private Upload avatar;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
