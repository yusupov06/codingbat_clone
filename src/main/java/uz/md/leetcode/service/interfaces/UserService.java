package uz.md.leetcode.service.interfaces;

import uz.md.leetcode.domain.User;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.payload.auth.UserUDTO;
import uz.md.leetcode.response.ApiResponse;

public interface UserService {
    ApiResponse<UserDTO> userMe();

}
