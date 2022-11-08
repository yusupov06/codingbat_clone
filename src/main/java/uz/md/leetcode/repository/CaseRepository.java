package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.domain.Case;


public interface CaseRepository extends JpaRepository<Case, Long > {
}
