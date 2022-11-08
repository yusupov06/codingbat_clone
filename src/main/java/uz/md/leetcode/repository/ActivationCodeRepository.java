package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.domain.ActivationCode;

import java.util.Optional;


public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {

    Optional<ActivationCode> findByActivationCode(String activationCode);
}
