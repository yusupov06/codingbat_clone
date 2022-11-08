package uz.md.leetcode.controller.interfaces;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import uz.md.leetcode.payload.LoginDTO;
import uz.md.leetcode.payload.TokenDTO;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.response.ApiResponse;

import javax.validation.Valid;



@RequestMapping(path = AuthController.AUTH_CONTROLLER_BASE_PATH)
public interface AuthController {

    String AUTH_CONTROLLER_BASE_PATH = "/api/auth";
    String SIGN_IN_PATH = "/sign-in";
    String SIGN_UP_PATH = "/sign-up";
    String VERIFICATION_PATH = "/verification-email";
    String REFRESH_TOKEN_PATH = "/refresh-token";

    @ApiOperation(value = "Sign up path")
    @PostMapping(value = SIGN_UP_PATH)
    ApiResponse<UserDTO> register(@RequestBody @Valid UserRegisterDTO registerDTO);

    @ApiOperation(value = "Verification path")
    @GetMapping(value = VERIFICATION_PATH)
    ApiResponse<?> verificationEmail(@RequestParam String email);

    @ApiOperation(value = "Sign in path")
    @PostMapping(value = SIGN_IN_PATH)
    ApiResponse<TokenDTO> login(@Valid @RequestBody LoginDTO signDTO);


    @ApiOperation(value = "Refresh token")
    @GetMapping(value = REFRESH_TOKEN_PATH)
    ApiResponse<TokenDTO> refreshToken(@RequestHeader(value = "Authorization") String accessToken,
                                       @RequestHeader(value = "RefreshToken") String refreshToken);

}
