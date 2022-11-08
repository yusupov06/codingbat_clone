package uz.md.leetcode.controller.interfaces;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.md.leetcode.payload.problem.ProblemCDTO;
import uz.md.leetcode.payload.problem.ProblemDTO;
import uz.md.leetcode.payload.problem.ProblemUDTO;

import javax.validation.Valid;
import java.util.List;



@RequestMapping(value = ProblemController.BASE_PATH)
public interface ProblemController {

    String BASE_PATH = "/api/v1/problem";
    String PROBLEMS_BY_SECTION_ID_PATH = BASE_PATH + "/all/by-section/{id}";
    String PROBLEM_ADD_PATH = BASE_PATH + "/add";
    String PROBLEM_UPDATE_PATH = BASE_PATH + "/update";
    String PROBLEM_GET_PATH = BASE_PATH + "/get/{id}";
    String PROBLEM_DELETE_PATH = BASE_PATH + "/delete/{id}";

    @ApiOperation(value = " getting all problems by section id")
    @GetMapping(PROBLEMS_BY_SECTION_ID_PATH)
    List<ProblemDTO> problemsBySectionId(@Valid @NonNull @PathVariable Integer id);

    @ApiOperation(value = " adding a problem ")
    @PostMapping(PROBLEM_ADD_PATH)
    ProblemDTO add(@Valid @NonNull ProblemCDTO problemCDTO);

    @ApiOperation(value = " Updating a problem ")
    @PostMapping(PROBLEM_UPDATE_PATH)
    ProblemDTO update(@Valid @NonNull ProblemUDTO problemUDTO);

    @ApiOperation(value = " get one by id ")
    @GetMapping(PROBLEM_GET_PATH)
    ProblemDTO get(@Valid @NonNull @PathVariable Integer id);

    @ApiOperation(value = " Delete by id")
    @GetMapping(PROBLEM_DELETE_PATH)
    void delete(@Valid @NonNull @PathVariable Integer id);


}
