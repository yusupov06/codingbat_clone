package uz.md.leetcode.controller.implementations;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.UserProblemController;
import uz.md.leetcode.payload.userProblem.UserProblemCDTO;
import uz.md.leetcode.payload.userProblem.UserProblemDTO;
import uz.md.leetcode.payload.userProblem.UserProblemUDTO;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.UserProblemService;

import java.util.List;



@RestController
public class UserProblemControllerImpl implements UserProblemController {

    private final UserProblemService service;

    public UserProblemControllerImpl(UserProblemService service) {
        this.service = service;
    }

    @Override
    public ApiResponse<List<UserProblemDTO>> getAllByUserId(@NonNull Integer id) {
        return service.findAllOfUser(id);
    }

    @Override
    public ApiResponse<UserProblemDTO> add(@NonNull UserProblemCDTO userProblemCDTO) {
        return service.add(userProblemCDTO);
    }

    @Override
    public ApiResponse<UserProblemDTO> update(@NonNull UserProblemUDTO userProblemUDTO) {
        return service.update(userProblemUDTO);
    }

    @Override
    public ApiResponse<UserProblemDTO> get(@NonNull Long id) {
        return service.get(id);
    }

    @Override
    public void delete(@NonNull Long id) {
        service.delete(id);
    }
}
