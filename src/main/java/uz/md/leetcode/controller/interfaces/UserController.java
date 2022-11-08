package uz.md.leetcode.controller.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.md.leetcode.config.security.CurrentUser;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.response.ApiResponse;



@RequestMapping(value = UserController.BASE_PATH)
public interface UserController {

    String BASE_PATH = "/api/user";

    String ME_PATH = "/me";

    @GetMapping(ME_PATH)
    ApiResponse<UserDTO> userMe();

}
