package uz.md.leetcode.payload.language;

import lombok.*;
import uz.md.leetcode.domain.Language;
import uz.md.leetcode.repository.ProblemRepository;
import uz.md.leetcode.repository.SectionRepository;
import uz.md.leetcode.repository.UserProblemRepository;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LanguageDTO {

    private Integer id;

    private String title;

    private String url;

    private Integer sectionCount;

    private Integer problemCount;

    private Long tryCount;

    private Long solutionCount;

    public LanguageDTO(Language language) {
        this.id = language.getId();
        this.title = language.getTitle();
        this.url = language.getUrl();
        this.problemCount = 0;
        this.sectionCount = 0;
        this.tryCount = 0L;
        this.solutionCount = 0L;
    }

    public LanguageDTO(Language language, Integer sectionCount, Long tryCount, Integer problemCount, Long solutionCount) {
        this(language);
        this.sectionCount = sectionCount;
        this.problemCount = problemCount;
        this.tryCount = tryCount;
        this.solutionCount = solutionCount;
    }


    public static List<LanguageDTO> list(List<Language> languages,
                                         SectionRepository sectionRepository,
                                         ProblemRepository problemRepository,
                                         UserProblemRepository userProblemRepository) {
        List<LanguageDTO> list = new ArrayList<>();

        for (Language language : languages) {
            Integer sectionCount = sectionRepository.countAllByLanguage_Id(language.getId());
            int problemCount = problemRepository.countAllBySection_Language_Id(language.getId());
            long tryCount = userProblemRepository.countAllByProblem_SectionLanguageIdJPQL(language.getId());
            long solvedCount = userProblemRepository.countAllBySolvedIsTrueAndProblem_Section_Language_Id(language.getId());
            list.add(new LanguageDTO(language, sectionCount, tryCount, problemCount, solvedCount));
        }
        return list;
    }

}
