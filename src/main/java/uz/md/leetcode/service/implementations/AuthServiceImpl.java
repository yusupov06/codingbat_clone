package uz.md.leetcode.service.implementations;

import freemarker.template.TemplateException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.domain.enums.RoleEnum;
import uz.md.leetcode.payload.LoginDTO;
import uz.md.leetcode.payload.TokenDTO;
import uz.md.leetcode.payload.auth.UserDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.mappers.UserMapper;
import uz.md.leetcode.repository.RoleRepository;
import uz.md.leetcode.repository.UserRepository;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.service.MailService;
import uz.md.leetcode.service.interfaces.AuthService;
import uz.md.leetcode.utils.MessageLang;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * Me: muhammadqodir
 * Project: CodingCompiler/IntelliJ IDEA
 * Date:Wed 14/09/22 11:11
 */

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Value("${jwt.access.secret}")
    private String ACCESS_TOKEN_KEY;
    @Value(("${jwt.refresh.secret}"))
    private String REFRESH_TOKEN_KEY;
    @Value("${jwt.access.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION_TIME;
    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;


    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           MailService mailService,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(
                        () -> RestException.restThrow(String.format("%s email not found", username), HttpStatus.UNAUTHORIZED));
    }

    @Override
    public ApiResponse<UserDTO> signUp(UserRegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw RestException.restThrow("EMAIL ALREADY EXIST", HttpStatus.CONFLICT);
        }

        User user = userMapper.fromCDTO(registerDTO);

        user.setRole(roleRepository.findByName(RoleEnum.ROLE_USER.name()).get());
        CompletableFuture.runAsync(() -> sendVerificationCodeToEmail(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        return ApiResponse.successResponse(userMapper.toDTO(save));
    }

    private void sendVerificationCodeToEmail(User user) {
        try {
            mailService.sendActivation(user);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponse<?> verificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> RestException.restThrow("EMAIL_NOT_EXIST", HttpStatus.NOT_FOUND));

        if (user.isEnabled()) {
            return ApiResponse.successResponse("ALREADY_VERIFIED");
        }

        user.setEnabled(true);
        userRepository.save(user);
        return ApiResponse.successResponse("SUCCESSFULLY_VERIFIED");
    }

    @Override
    public ApiResponse<TokenDTO> signIn(LoginDTO signDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signDTO.getEmail(),
                        signDTO.getPassword()
                ));

        User user = (User) authentication.getPrincipal();

        String refreshToken = generateToken(user.getEmail(), true);
        String accessToken = generateToken(user.getEmail(), false);

        TokenDTO tokenDTO = TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResponse.successResponse("TOKEN_SUCCESSFULLY_GENERATED", tokenDTO);

    }

    @Override
    public ApiResponse<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        accessToken = accessToken.substring(accessToken.indexOf("Bearer") + 6).trim();
        try {
            Jwts
                    .parser()
                    .setSigningKey(ACCESS_TOKEN_KEY)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            try {
                String email = Jwts
                        .parser()
                        .setSigningKey(REFRESH_TOKEN_KEY)
                        .parseClaimsJws(refreshToken)
                        .getBody()
                        .getSubject();
                User user = userRepository.findByEmail(email).orElseThrow(() ->
                        RestException.restThrow(MessageLang.getMessageSource("EMAIL_NOT_EXIST"), HttpStatus.NOT_FOUND));

                if (!user.isEnabled()
                        || !user.isAccountNonExpired()
                        || !user.isAccountNonLocked()
                        || !user.isCredentialsNonExpired())
                    throw RestException.restThrow(MessageLang.getMessageSource("USER_PERMISSION_RESTRICTION"), HttpStatus.UNAUTHORIZED);

                String newAccessToken = generateToken(email, true);
                String newRefreshToken = generateToken(email, false);
                TokenDTO tokenDTO = TokenDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                return ApiResponse.successResponse(tokenDTO);
            } catch (Exception e) {
                throw RestException.restThrow(MessageLang.getMessageSource("REFRESH_TOKEN_EXPIRED"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow(MessageLang.getMessageSource("WRONG_ACCESS_TOKEN"), HttpStatus.UNAUTHORIZED);
        }

        throw RestException.restThrow(MessageLang.getMessageSource("ACCESS_TOKEN_NOT_EXPIRED"), HttpStatus.UNAUTHORIZED);
    }

    public String generateToken(String email, boolean accessToken) {

        Date expiredDate = new Date(new Date().getTime() +
                (accessToken ? ACCESS_TOKEN_EXPIRATION_TIME : REFRESH_TOKEN_EXPIRATION_TIME));

        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, (
                        accessToken ? ACCESS_TOKEN_KEY : REFRESH_TOKEN_KEY
                ))
                .compact();
    }

}
