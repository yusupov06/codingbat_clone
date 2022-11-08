package uz.md.leetcode.controller.implementations;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.ProblemController;
import uz.md.leetcode.payload.problem.ProblemCDTO;
import uz.md.leetcode.payload.problem.ProblemDTO;
import uz.md.leetcode.payload.problem.ProblemUDTO;
import uz.md.leetcode.service.interfaces.ProblemService;

import java.util.List;


@RestController
public class ProblemControllerImpl implements ProblemController {


    private final ProblemService problemService;

    public ProblemControllerImpl(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Override
    public List<ProblemDTO> problemsBySectionId(@NonNull Integer id) {
        return problemService.findAllBySectionID(id);
    }

    @Override
    public ProblemDTO add(@NonNull ProblemCDTO problemCDTO) {
        return problemService.add(problemCDTO);
    }

    @Override
    public ProblemDTO update(@NonNull ProblemUDTO problemUDTO) {
        return problemService.update(problemUDTO);
    }

    @Override
    public ProblemDTO get(@NonNull Integer id) {
        return problemService.get(id);
    }

    @Override
    public void delete(@NonNull Integer id) {
        problemService.delete(id);
    }
}
