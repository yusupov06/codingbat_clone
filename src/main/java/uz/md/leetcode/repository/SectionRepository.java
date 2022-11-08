package uz.md.leetcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.md.leetcode.domain.Section;
import uz.md.leetcode.projection.SectionDTOProjection;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Integer> {


    Integer countAllByLanguage_Id(Integer id);
    boolean existsByLanguageId(Integer language_id);

    boolean existsByTitle(String title);

    boolean existsById(Integer id);

    @Query(value = "select * from get_query_result(:query)", nativeQuery = true)
    List<SectionDTOProjection> getSectionsByStringQuery(String query);

}
