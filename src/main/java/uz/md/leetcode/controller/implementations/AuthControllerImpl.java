package uz.md.leetcode.controller.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.md.leetcode.controller.interfaces.AuthController;
import uz.md.leetcode.payload.LoginDTO;
import uz.md.leetcode.payload.TokenDTO;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.implementations.AuthServiceImpl;
import uz.md.leetcode.service.interfaces.AuthService;



@RestController
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    public AuthControllerImpl(@Lazy AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    public ApiResponse<UserDTO> register( UserRegisterDTO registerDTO) {
        log.info(" Register method entered with {}",registerDTO);
        ApiResponse<UserDTO> apiResponse = authService.signUp(registerDTO);
        log.info(" Register method exited: {}", apiResponse);
        return apiResponse;
    }

    @Override
    public ApiResponse<?> verificationEmail(String email) {
        log.info(" Verification email method entered ");
        ApiResponse<?> apiResponse = authService.verificationEmail(email);
        log.info(" Verification method exited: {}", apiResponse);
        return apiResponse;
    }

    @Override
    public ApiResponse<TokenDTO> login(LoginDTO signDTO) {
        log.info("login method entered: {}", signDTO);
        ApiResponse<TokenDTO> apiResponse = authService.signIn(signDTO);
        log.info("login method exited: {}", apiResponse);
        return apiResponse;
    }

    @Override
    public ApiResponse<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        log.info("refresh token method : {}, {}", accessToken,refreshToken);
        ApiResponse<TokenDTO> apiResponse = authService.refreshToken(accessToken, refreshToken);
        log.info("refresh token method : {}", apiResponse);
        return apiResponse;
    }
}
