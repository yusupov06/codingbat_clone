package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.md.leetcode.domain.UserProblem;
import uz.md.leetcode.payload.userProblem.UserProblemDTO;
import uz.md.leetcode.response.ApiResponse;

import java.util.List;

@Repository
public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {

    @Query("select count (up) from UserProblem  up join Problem p ON p = up.problem JOIN Section s on s = p.section where s.language.id = :id")
    long countAllByProblem_SectionLanguageIdJPQL(@Param("id") Integer languageId);
    long countAllBySolvedIsTrueAndProblem_Section_Language_Id(Integer id);
    long countAllBySolvedIsFalseAndProblem_Section_Id(Integer problem_section_id);
    List<UserProblem> findAllByUser_IdAndProblem_Section_Id(Integer user_id, Integer problem_section_id);
    List<UserProblem> findAllByUserId(Integer user_id);

    @Modifying
    @Query("update UserProblem set deleted = true where id = :id")
    void softDeleteById(Long id);
}
