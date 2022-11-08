package uz.md.leetcode.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.md.leetcode.payload.LoginDTO;
import uz.md.leetcode.payload.TokenDTO;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.response.ApiResponse;


public interface AuthService extends UserDetailsService {

    ApiResponse<UserDTO> signUp(UserRegisterDTO registerDTO);

    ApiResponse<?> verificationEmail(String email);

    ApiResponse<TokenDTO> signIn(LoginDTO signDTO);

    ApiResponse<TokenDTO> refreshToken(String accessToken, String refreshToken);

}
