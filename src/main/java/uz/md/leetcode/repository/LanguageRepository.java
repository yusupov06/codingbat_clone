package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.md.leetcode.domain.Language;
import uz.md.leetcode.projection.LanguageDTOProjection;

import java.util.List;


public interface LanguageRepository extends JpaRepository<Language, Integer> {

    boolean existsByTitleIgnoreCase(String title);

    boolean existsById(Integer id);

    @Query(value = "SELECT * FROM get_result_of_query(:query)", nativeQuery = true)
    List<LanguageDTOProjection> getLanguagesByQuery(String query);

}
