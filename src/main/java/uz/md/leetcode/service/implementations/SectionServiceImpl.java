package uz.md.leetcode.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.Language;
import uz.md.leetcode.domain.Problem;
import uz.md.leetcode.domain.Section;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.mappers.SectionMapper;
import uz.md.leetcode.payload.FilterColumnDTO;
import uz.md.leetcode.payload.SortingDTO;
import uz.md.leetcode.payload.ViewDTO;
import uz.md.leetcode.payload.enums.ConditionTypeEnum;
import uz.md.leetcode.payload.section.SectionCDTO;
import uz.md.leetcode.payload.section.SectionDTO;
import uz.md.leetcode.payload.section.SectionUDTO;
import uz.md.leetcode.projection.SectionDTOProjection;
import uz.md.leetcode.repository.LanguageRepository;
import uz.md.leetcode.repository.ProblemRepository;
import uz.md.leetcode.repository.SectionRepository;
import uz.md.leetcode.repository.UserProblemRepository;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.SectionService;

import java.util.*;



@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;
    private final LanguageRepository languageRepository;
    private final SectionMapper mapper;
    private final SectionRepository sectionRepository;
    private final ProblemRepository problemRepository;
    private final UserProblemRepository userProblemRepository;


    @Override
    public ApiResponse<SectionDTO> add(SectionCDTO sectionDTO) {

        if (repository.existsByLanguageId(sectionDTO.getLanguageId())
                && repository.existsByTitle(sectionDTO.getTitle()))
            throw RestException.restThrow("SECTION_ALREADY_EXISTS", HttpStatus.CONFLICT);

        Language language = languageRepository.findById(sectionDTO.getLanguageId())
                .orElseThrow(() -> RestException.restThrow("LANGUAGE_NOT_FOUND", HttpStatus.NOT_FOUND));

        Section section = mapper.fromCDTO(sectionDTO);
        section.setLanguage(language);
        Section save = repository.save(section);

        SectionDTO sectionDTO1 = mapper.toDTO(save);
        sectionDTO1.setLanguageId(language.getId());
        sectionDTO1.setProblemCount(0L);
        sectionDTO1.setSolutionCount(0L);

        return ApiResponse
                .successResponse(sectionDTO1);
    }

    @Override
    public ApiResponse<SectionDTO> getSection(Integer id) {
        Section section = repository.findById(id)
                .orElseThrow(() -> RestException.restThrow("SECTION_NOT_FOUND", HttpStatus.NOT_FOUND));
        SectionDTO sectionDTO = mapper.toDTO(section);
        sectionDTO.setLanguageId(section.getLanguage().getId());

        setSectionProblemAndSolutionCount(sectionDTO, section);

        return ApiResponse.successResponse(sectionDTO);
    }

    private void setSectionProblemAndSolutionCount(SectionDTO sectionDTO, Section section) {
        long problemsCount = problemRepository.countAllBySection_Id(section.getId());
        long solvedCount = userProblemRepository.countAllBySolvedIsTrueAndProblem_Section_Language_Id(section.getLanguage().getId());
        long tryCount = userProblemRepository.countAllBySolvedIsFalseAndProblem_Section_Id(section.getId());
        sectionDTO.setProblemCount(problemsCount);
        sectionDTO.setSolutionCount(solvedCount);
        sectionDTO.setTryCount(tryCount);
    }

    @Override
    public ApiResponse<Void> delete(Integer id) {
        repository.deleteById(id);
        return ApiResponse.successResponse("SUCCESSFULLY_DELETED");
    }

    @Override
    public ApiResponse<SectionDTO> edit(SectionUDTO sectionUDTO) {


        Section section = mapper.fromUDTO(sectionUDTO, repository.findById(sectionUDTO.getId())
                .orElseThrow(() -> RestException.restThrow("SECTION_NOT_FOUND", HttpStatus.NOT_FOUND)));

        Language language = languageRepository.findById(sectionUDTO.getLanguageId())
                .orElseThrow(() -> RestException.restThrow("LANGUAGE_NOT_FOUND", HttpStatus.NOT_FOUND));


        section.setLanguage(language);
        SectionDTO sectionDTO = mapper.toDTO(section);

        Section save = repository.save(section);

        sectionDTO.setLanguageId(save.getLanguage().getId());

        setSectionProblemAndSolutionCount(sectionDTO, save);

        return ApiResponse.successResponse(sectionDTO);
    }

    @Override
    public ApiResponse<List<SectionDTOProjection>> getSections(ViewDTO viewDTO, int page, int size) {

        StringBuilder queryBuilder = new StringBuilder(
                """
                        WITH temp AS (
                            SELECT s.*,
                                   COUNT(p.id) AS problemCount,
                                   COUNT(up.id) AS tryCount,
                                   COUNT(CASE WHEN up.solved THEN up.id end) AS solutionCount
                                   FROM section s
                                   LEFT JOIN problem p ON s.id = p.section_id
                                   LEFT JOIN user_problem up ON p.id = up.problem_id
                                   GROUP BY s.id)
                        SELECT * FROM temp """
        );

        if (Objects.isNull(viewDTO)){

            queryBuilder.append(" ORDER BY title; ");

            List<SectionDTOProjection> sectionDTOProjections = sectionRepository.getSectionsByStringQuery(queryBuilder.toString());

            return ApiResponse.successResponse(sectionDTOProjections);
        }

        if (viewDTO.getFiltering().getColumns().isEmpty()) {
            if (viewDTO.getSearching().getValue().isBlank() && viewDTO.getSorting().isEmpty()) {
                queryBuilder.append("ORDER BY title");
            }
        }

        boolean checkSearching = false;

        if (!viewDTO.getSearching().getColumns().isEmpty()) {
            checkSearching = true;
            queryBuilder.append(" WHERE ");

            for (String column : viewDTO.getSearching().getColumns()) {
                queryBuilder
                        .append(column)
                        .append(" ILIKE ")
                        .append("'%")
                        .append(viewDTO.getSearching().getValue())
                        .append("%'")
                        .append(" AND ");
            }
            queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());
        }

        if (!viewDTO.getFiltering().getColumns().isEmpty()) {

            boolean checkFirst = false;
            if (!checkSearching)
                queryBuilder.append(" WHERE ");
            else
                queryBuilder.append(" AND ( ");

            for (FilterColumnDTO column : viewDTO.getFiltering().getColumns()) {
                if (checkFirst)
                    queryBuilder.append(" ").append(viewDTO.getFiltering().getOperatorType()).append(" ");
                checkFirst = true;
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.CONTAINS)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" ILIKE ")
                            .append("'%")
                            .append(column.getValue())
                            .append("%'");
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.NOT_CONTAINS)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" NOT ILIKE ")
                            .append("'%")
                            .append(column.getValue())
                            .append("%'");
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.IS_SET)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" IS NOT NULL ");
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.IS_NOT_SET)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" IS NULL ");
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.EQ)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" = ")
                            .append(column.getValue());
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.NOT_EQ)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" <> ")
                            .append(column.getValue());
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.GT)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" > ")
                            .append(column.getValue());
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.GTE)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" >= ")
                            .append(column.getValue());
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.LT)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" < ")
                            .append(column.getValue());
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.LTE)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" <= ")
                            .append(column.getValue());
                }
                if (Objects.equals(column.getConditionType(), ConditionTypeEnum.RA)) {
                    queryBuilder
                            .append(column.getName())
                            .append(" BETWEEN ")
                            .append(column.getFrom())
                            .append(" AND ")
                            .append(column.getTill());
                }
            }


            if (!viewDTO.getSorting().isEmpty()) {
                queryBuilder.append(" ORDER BY ");
                for (SortingDTO sortingDTO : viewDTO.getSorting()) {
                    queryBuilder
                            .append(sortingDTO.getName())
                            .append(" ")
                            .append(sortingDTO.getType())
                            .append(", ");
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
            } else {
                queryBuilder.append(" ORDER BY TITLE");
            }

            queryBuilder
                    .append(" LIMIT ")
                    .append(size)
                    .append(" OFFSET ")
                    .append(page * size);

        }

        String query = queryBuilder.toString();
        List<SectionDTOProjection> sectionDTOProjections = sectionRepository.getSectionsByStringQuery(query);

        return ApiResponse.successResponse(sectionDTOProjections);

    }

    @Override
    public ApiResponse<List<SectionDTO>> getSectionsForUser() {
        List<Section> all = sectionRepository.findAll();
        return ApiResponse.successResponse(mapper.toDTO(all));
    }
}
