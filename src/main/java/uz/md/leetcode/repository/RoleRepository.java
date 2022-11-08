package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.domain.Role;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
