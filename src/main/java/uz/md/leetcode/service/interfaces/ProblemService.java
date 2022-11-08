package uz.md.leetcode.service.interfaces;

import org.springframework.stereotype.Service;
import uz.md.leetcode.payload.problem.ProblemCDTO;
import uz.md.leetcode.payload.problem.ProblemDTO;
import uz.md.leetcode.payload.problem.ProblemUDTO;

import java.util.List;


@Service
public interface ProblemService {


    List<ProblemDTO> findAllBySectionID(Integer id);

    List<ProblemDTO> findAll();

    ProblemDTO add(ProblemCDTO problemCDTO);

    ProblemDTO update(ProblemUDTO problemUDTO);

    ProblemDTO get(Integer id);

    void delete(Integer id);
}
