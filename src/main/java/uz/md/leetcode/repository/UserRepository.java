package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.domain.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}
