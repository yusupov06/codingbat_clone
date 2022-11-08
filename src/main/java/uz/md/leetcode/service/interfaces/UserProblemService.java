package uz.md.leetcode.service.interfaces;

import uz.md.leetcode.payload.problem.ProblemCDTO;
import uz.md.leetcode.payload.problem.ProblemDTO;
import uz.md.leetcode.payload.problem.ProblemUDTO;
import uz.md.leetcode.payload.userProblem.UserProblemCDTO;
import uz.md.leetcode.payload.userProblem.UserProblemDTO;
import uz.md.leetcode.payload.userProblem.UserProblemUDTO;
import uz.md.leetcode.response.ApiResponse;

import java.util.List;


public interface UserProblemService {

    ApiResponse<List<UserProblemDTO>> findAllBySectionID(Integer id);

    ApiResponse<List<UserProblemDTO>> findAllOfUser(Integer id);

    ApiResponse<UserProblemDTO> add(UserProblemCDTO problemCDTO);

    ApiResponse<UserProblemDTO> update(UserProblemUDTO userProblemUDTO);

    ApiResponse<UserProblemDTO> get(Long id);

    void delete(Long id);

}
