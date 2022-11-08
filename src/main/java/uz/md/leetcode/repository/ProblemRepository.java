package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.md.leetcode.domain.Problem;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {

    int countAllBySection_Language_Id(Integer languageId);

    Optional<List<Problem>> findAllBySection_Id(Integer section_id);

    long countAllBySection_Id(Integer section_id);
}
