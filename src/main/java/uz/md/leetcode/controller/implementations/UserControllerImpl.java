package uz.md.leetcode.controller.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.UserController;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.interfaces.UserService;


@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ApiResponse<UserDTO> userMe() {
        return userService.userMe();
    }

}
