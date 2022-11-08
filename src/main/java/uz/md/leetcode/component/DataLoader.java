package uz.md.leetcode.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.md.leetcode.domain.Role;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.domain.enums.PermissionEnum;
import uz.md.leetcode.domain.enums.RoleEnum;
import uz.md.leetcode.repository.RoleRepository;
import uz.md.leetcode.repository.UserRepository;

import java.util.Set;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) throws Exception {

        if (ddlMode.equals("create")) {
            Role role = new Role();
            role.setName(RoleEnum.ROLE_ADMIN.name());
            role.setDescription("Admin role");
            role.setPermissions(Set.of(PermissionEnum.values()));
            roleRepository.save(role);

            Role user = new Role();
            user.setName(RoleEnum.ROLE_USER.name());
            user.setDescription("User role");
            user.setPermissions(Set.of(PermissionEnum.SOLVE_PROBLEM));
            roleRepository.save(user);

            User admin = new User(adminEmail, passwordEncoder.encode(adminPassword));

            admin.setRole(role);
            admin.setEnabled(true);
            userRepository.save(admin);
        }

    }
}
