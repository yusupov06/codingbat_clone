package uz.md.leetcode.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.Problem;
import uz.md.leetcode.payload.problem.ProblemCDTO;
import uz.md.leetcode.payload.problem.ProblemDTO;
import uz.md.leetcode.payload.problem.ProblemUDTO;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.mappers.ProblemMapper;
import uz.md.leetcode.repository.ProblemRepository;
import uz.md.leetcode.service.interfaces.ProblemService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Me: muhammadqodir
 * Project: Leetcode/IntelliJ IDEA
 * Date:Tue 20/09/22 16:19
 */

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemMapper mapper;

    @Override
    public List<ProblemDTO> findAllBySectionID(Integer id) {
        return problemRepository
                .findAllBySection_Id(id)
                .orElseThrow(() -> RestException.restThrow("SECTION_ID_NO_PROBLEMS", HttpStatus.NOT_FOUND))
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<ProblemDTO> findAll() {
        return null;
    }

    @Override
    public ProblemDTO add(ProblemCDTO problemCDTO) {
        Problem problem = mapper.fromCDTO(problemCDTO);
        return mapper.toDTO(problemRepository.save(problem));
    }

    @Override
    public ProblemDTO update(ProblemUDTO problemUDTO) {
        Problem problem = problemRepository.findById(problemUDTO.getId())
                .orElseThrow(() -> RestException.restThrow("PROBLEM_NOT_FOUND", HttpStatus.NOT_FOUND));
        Problem problem1 = mapper.fromUDTO(problemUDTO, problem);
        return mapper.toDTO(problemRepository.save(problem1));

    }

    @Override
    public ProblemDTO get(Integer id) {
        return
                mapper
                .toDTO(problemRepository
                .findById(id)
                .orElseThrow(() -> RestException.restThrow("PROBLEM_NOT_FOUND", HttpStatus.NOT_FOUND)));
    }

    @Override
    public void delete(Integer id) {
        problemRepository.deleteById(id);
    }
}
