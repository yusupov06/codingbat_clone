package uz.md.leetcode.controller.interfaces;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.md.leetcode.payload.userProblem.UserProblemCDTO;
import uz.md.leetcode.payload.userProblem.UserProblemDTO;
import uz.md.leetcode.payload.userProblem.UserProblemUDTO;
import uz.md.leetcode.response.ApiResponse;

import javax.validation.Valid;
import java.util.List;



@RequestMapping(value = UserProblemController.BASE_PATH)
public interface UserProblemController {

    String BASE_PATH = "/api/v1/userProblem";

    @ApiOperation(value = " getting all userProblems ")
    @GetMapping(BASE_PATH + "/all/of/user/{id}")
    ApiResponse<List<UserProblemDTO>> getAllByUserId(@Valid @NonNull @PathVariable Integer id);

    @ApiOperation(value = " adding a userProblem ")
    @PostMapping(BASE_PATH + "/add")
    ApiResponse<UserProblemDTO> add(@Valid @NonNull UserProblemCDTO userProblemCDTO);

    @ApiOperation(value = " Updating a userProblem ")
    @PostMapping(BASE_PATH + "/update")
    ApiResponse<UserProblemDTO> update(@Valid @NonNull UserProblemUDTO userProblemUDTO);

    @ApiOperation(value = " get one by id ")
    @GetMapping(BASE_PATH + "/get/{id}")
    ApiResponse<UserProblemDTO> get(@Valid @NonNull @PathVariable Long id);

    @ApiOperation(value = " Delete by id")
    @GetMapping(BASE_PATH + "/delete/{id}")
    void delete(@Valid @NonNull @PathVariable Long id);

}
