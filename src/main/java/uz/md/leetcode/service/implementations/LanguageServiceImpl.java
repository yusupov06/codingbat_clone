package uz.md.leetcode.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.Language;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.payload.*;
import uz.md.leetcode.payload.enums.ConditionTypeEnum;
import uz.md.leetcode.payload.language.AddLanguageDTO;
import uz.md.leetcode.payload.language.LanguageDTO;
import uz.md.leetcode.projection.LanguageDTOProjection;
import uz.md.leetcode.repository.LanguageRepository;
import uz.md.leetcode.repository.ProblemRepository;
import uz.md.leetcode.repository.SectionRepository;
import uz.md.leetcode.repository.UserProblemRepository;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.LanguageService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Me: muhammadqodir
 * Project: Leetcode/IntelliJ IDEA
 * Date:Tue 20/09/22 16:26
 */

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final SectionRepository sectionRepository;
    private final ProblemRepository problemRepository;
    private final UserProblemRepository userProblemRepository;


    private LanguageDTO mapToDTO(Language language) {
        return new LanguageDTO(language);
    }

    @Override
    public ApiResponse<LanguageDTO> add(AddLanguageDTO addLanguageDTO) {
        if (languageRepository.existsByTitleIgnoreCase(addLanguageDTO.getTitle())) {
            throw new RestException("LANGUAGE_ALREADY_EXISTS", HttpStatus.CONFLICT);
        }

        Language language = new Language();
        language.setTitle(addLanguageDTO.getTitle());
        Language save = languageRepository.save(language);

        return ApiResponse.successResponse("Successfully added", mapToDTO(save));

    }

    @Override
    public ApiResponse<LanguageDTO> findById(Integer id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RestException("LANGUAGE_NOT_FOUND", HttpStatus.NOT_FOUND));
        return ApiResponse.successResponse(mapToDTO(language));
    }

    //    @PreAuthorize("hasAuthority('DELETE_LANGUAGE')")
    @Override
    public ApiResponse<Boolean> deleteById(Integer id) {
        languageRepository.deleteById(id);
        return ApiResponse.successResponse(true);
    }

    @Override
//    @PreAuthorize("hasAuthority('EDIT_LANGUAGE')")
    public ApiResponse<LanguageDTO> edit(AddLanguageDTO languageDTO, Integer id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RestException("LANGUAGE_NOT_FOUND", HttpStatus.NOT_FOUND));

        if (Objects.nonNull(languageDTO.getTitle())) {
            language.setTitle(languageDTO.getTitle());
        }

        Language save = languageRepository.save(language);

        return ApiResponse.successResponse(mapToDTO(save));
    }

    @Override
    public ApiResponse<List<LanguageDTO>> getLanguagesForUser() {
        List<Language> languages = languageRepository.findAll();

        List<LanguageDTO> list = LanguageDTO.list(languages,
                sectionRepository,
                problemRepository,
                userProblemRepository);


        return ApiResponse.successResponse(list);

    }

    @Override
    public ApiResponse<List<LanguageDTOProjection>> getLanguagesBySuperMethod(ViewDTO viewDTO, int page, int size) {

        StringBuilder queryBuilder = new StringBuilder(
                """
                        with temp as(
                         SELECT l.*,
                         COUNT(s.id)                               AS sectionCount,
                         COUNT(p.id)                               AS problemCount,
                         COUNT(up.id)                              AS tryCount,
                         COUNT(CASE WHEN up.solved THEN up.id END) AS solutionCount
                                        FROM language l
                                                LEFT JOIN section s on l.id = s.language_id
                                                LEFT JOIN problem p on s.id = p.section_id
                                                LEFT JOIN user_problem up on p.id = up.problem_id""");


        if (Objects.isNull(viewDTO)) {

            queryBuilder.append(" GROUP BY l.id  ) \n");
            queryBuilder.append(" SELECT * FROM temp \n ");

            String s = queryBuilder.toString();
            System.out.println("query = " + s);
            List<LanguageDTOProjection> languagesByQuery = languageRepository.getLanguagesByQuery(s);
            return ApiResponse.successResponse(languagesByQuery);
        }

        mainWhere(queryBuilder, viewDTO.getFiltering(), viewDTO.getSearching());
        queryBuilder.append(" GROUP BY l.id  ) \n");
        queryBuilder.append(" SELECT * FROM temp \n");

        whereForHaving(queryBuilder, viewDTO.getFiltering());
        orderBy(queryBuilder, viewDTO.getSorting());

        queryBuilder
                .append(" LIMIT ")
                .append(size)
                .append(" OFFSET ")
                .append(page * size).append(" ");

        String query = queryBuilder.toString();
        System.out.println(query);
        List<LanguageDTOProjection> languagesByStringQuery = languageRepository.getLanguagesByQuery(query);
        languagesByStringQuery.sort(Comparator.comparingInt(LanguageDTOProjection::getId));
        return ApiResponse.successResponse(languagesByStringQuery);
    }

    private void mainWhere(StringBuilder queryBuilder, FilterDTO filter, SearchingDTO search) {

        if ((filter == null ||
                forFilter(filter.getColumns(), true).isEmpty())
                && (search == null || search.getColumns().isEmpty()))
            return;
        queryBuilder.append("\n WHERE ");

        boolean filtered = false;

        List<FilterColumnDTO> filterColumns;
        if (filter != null
                && !(filterColumns = forFilter(filter.getColumns(), true)).isEmpty()) {
            String operatorType = " " + filter.getOperatorType().toString() + " ";
            FilterColumnDTO item = filterColumns.get(0);
            subWhere(queryBuilder, item.getName(), item.getConditionType(),
                    item.getValue(), item.getFrom(), item.getTill());
            filtered = true;
            for (int i = 1; i < filterColumns.size(); i++) {
                item = filterColumns.get(i);
                queryBuilder.append(operatorType);
                subWhere(queryBuilder, item.getName(), item.getConditionType(),
                        item.getValue(), item.getFrom(), item.getTill());
            }
        }

        List<String> searchingColumns;
        if (search != null
                && !(searchingColumns = search.getColumns()).isEmpty()) {
            String operatorType = " OR ";

            if (filtered)
                queryBuilder.append(" OR ");

            String item = searchingColumns.get(0);
            String val = search.getValue();
            subWhere(queryBuilder, item, ConditionTypeEnum.CONTAINS,
                    val, null, null);
            for (int i = 1; i < searchingColumns.size(); i++) {
                item = searchingColumns.get(i);
                queryBuilder.append(operatorType);
                subWhere(queryBuilder, item, ConditionTypeEnum.CONTAINS,
                        val, null, null);
            }
        }

    }

    private void whereForHaving(StringBuilder sb, FilterDTO filterDTO) {
        if (filterDTO == null || forFilter(filterDTO.getColumns(), false).isEmpty())
            return;
        sb.append("\n WHERE ");
        List<FilterColumnDTO> list = forFilter(filterDTO.getColumns(), false);
        String operator = filterDTO.getOperatorType().toString();

        subWhere(sb, list.get(0).getName(), list.get(0).getConditionType(),
                list.get(0).getValue(), list.get(0).getFrom(), list.get(0).getTill());

        for (int i = 1; i < list.size(); i++)
            subWhere(sb.append(" ").append(operator).append(" "), list.get(0).getName(), list.get(0).getConditionType(),
                    list.get(0).getValue(), list.get(0).getFrom(), list.get(0).getTill());
    }

    private void orderBy(StringBuilder sb, List<SortingDTO> list) {
        sb.append("\n ORDER BY ");
        if (list == null || list.isEmpty()) {
            sb.append(" title ");
            return;
        }

        sb.append(" ").append(list.get(0).getName()).append(" ").append(list.get(0).getType().toString());
        for (int i = 1; i < list.size(); i++)
            sb.append(" , ").append(list.get(i).getName()).append(" ")
                    .append(list.get(i).getType().toString()).append(" ");

    }

    private void subWhere(StringBuilder queryBuilder, String column, ConditionTypeEnum cond,
                          String val, String from, String till) {
        if ((column.equals("title") || column.equals("url")))
            queryBuilder.append(" l.").append(column).append(" ");
        else
            queryBuilder.append(" ").append(column).append(" ");

        String res = switch (cond) {
            case IS_SET -> " IS NOT NULL ";
            case IS_NOT_SET -> (" IS NULL");
            case CONTAINS -> (" ILIKE '%" + val + "%'");
            case NOT_CONTAINS -> (" NOT ILIKE '%" + val + "%'");
            case EQ -> (" = " + val);
            case NOT_EQ -> (" != " + val);
            case GT -> (" > " + val);
            case LT -> (" < " + val);
            case GTE -> (" >= " + val);
            case LTE -> (" <= " + val);
            case RA -> (" BETWEEN " + from + " AND " + till + " ");
        };
        queryBuilder.append(res);
    }

    private List<FilterColumnDTO> forFilter(List<FilterColumnDTO> columns, boolean needTitleAndUrl) {
        List<FilterColumnDTO> list = new ArrayList<>();
        if (needTitleAndUrl) {
            for (FilterColumnDTO column : columns) {
                String name = column.getName();
                if (name.equalsIgnoreCase("title") || name.equalsIgnoreCase("url")) {
                    list.add(column);
                }
            }
        } else {
            for (FilterColumnDTO column : columns) {
                String name = column.getName();
                if (!(name.equalsIgnoreCase("title") || name.equalsIgnoreCase("url"))) {
                    list.add(column);
                }
            }
        }

        return list;
    }
}
