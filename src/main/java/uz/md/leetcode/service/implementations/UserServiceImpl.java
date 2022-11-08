package uz.md.leetcode.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.mappers.UserMapper;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.repository.UserRepository;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.UserService;

import java.util.Optional;

/**
 * Me: muhammadqodir
 * Project: CodingCompiler/IntelliJ IDEA
 * Date:Wed 14/09/22 11:00
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ApiResponse<UserDTO> userMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.successResponse(userMapper.toDTO(user));
    }
}
