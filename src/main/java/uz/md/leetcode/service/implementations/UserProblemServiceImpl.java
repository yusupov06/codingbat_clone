package uz.md.leetcode.service.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.UserProblem;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.mappers.UserProblemMapper;
import uz.md.leetcode.payload.userProblem.UserProblemCDTO;
import uz.md.leetcode.payload.userProblem.UserProblemDTO;
import uz.md.leetcode.payload.userProblem.UserProblemUDTO;
import uz.md.leetcode.repository.UserProblemRepository;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.UserProblemService;

import java.util.List;

/**
 * Me: muhammadqodir
 * Project: Leetcode/IntelliJ IDEA
 * Date:Wed 21/09/22 15:47
 */

@Service
public class UserProblemServiceImpl implements UserProblemService {

    private final UserProblemRepository repository;
    private final UserProblemMapper userProblemMapper;

    public UserProblemServiceImpl(UserProblemRepository repository, UserProblemMapper userProblemMapper) {
        this.repository = repository;
        this.userProblemMapper = userProblemMapper;
    }

    @Override
    public ApiResponse<List<UserProblemDTO>> findAllBySectionID(Integer id) {
        return ApiResponse.successResponse(
                repository.findAllByUser_IdAndProblem_Section_Id(5, id)
                        .stream()
                        .map(userProblemMapper::toDTO)
                        .toList()
        );
    }

    @Override
    public ApiResponse<List<UserProblemDTO>> findAllOfUser(Integer id) {
        return ApiResponse.successResponse(
                repository.findAllByUserId(id)
                        .stream()
                        .map(userProblemMapper::toDTO)
                        .toList()
        );
    }

    @Override
    public ApiResponse<UserProblemDTO> add(UserProblemCDTO problemCDTO) {
        UserProblem userProblem = userProblemMapper.fromCDTO(problemCDTO);
        UserProblem save = repository.save(userProblem);
        return ApiResponse.successResponse(userProblemMapper.toDTO(save));
    }

    @Override
    public ApiResponse<UserProblemDTO> update(UserProblemUDTO userProblemUDTO) {
        UserProblem userProblem = repository.findById(userProblemUDTO.getId()).orElseThrow(() -> RestException.restThrow(" User Problem not found", HttpStatus.NOT_FOUND));
        userProblem.setSolution(userProblemUDTO.getSolution());
        UserProblem save = repository.save(userProblem);
        return ApiResponse.successResponse(userProblemMapper.toDTO(save));
    }

    @Override
    public ApiResponse<UserProblemDTO> get(Long id) {
        UserProblem userProblem = repository.findById(id).orElseThrow(() -> RestException.restThrow("User problem not found", HttpStatus.NOT_FOUND));
        return ApiResponse.successResponse(userProblemMapper.toDTO(userProblem));
    }

    @Override
    public void delete(Long id) {
        repository.softDeleteById(id);
    }
}
