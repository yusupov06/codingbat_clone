package uz.md.leetcode.api.auth;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.payload.LoginDTO;
import uz.md.leetcode.payload.TokenDTO;
import uz.md.leetcode.payload.auth.UserRegisterDTO;
import uz.md.leetcode.response.ApiErrorResponse;
import uz.md.leetcode.response.ApiResponse;
import uz.md.leetcode.utils.BaseUtils;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(value = 1)
public class AuthControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private static User user;

    @Mock
    private static UserRegisterDTO userRegisterDTO;

    @Mock
    private static LoginDTO loginDTO;

    private static String adminAccessToken;
    private static String tokenType;
    private static String adminRefreshToken;

    @BeforeAll
    static void setMocks() {
        userRegisterDTO = new UserRegisterDTO("yusupovforwin@gmail.com", "yusupov2002");
        user = new User(userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
    }

    @Test
    public void signUpSuccessfullyTest() throws Exception {

        userRegisterDTO = new UserRegisterDTO("yusupov2@gmail.com", "yusupov");
        user = new User(userRegisterDTO.getEmail(), passwordEncoder.encode(userRegisterDTO.getPassword()));

        ResultActions userRegister = mockMvc.perform(
                post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(BaseUtils.objectToJson(userRegisterDTO)))
        );

        String contentAsString = userRegister.andReturn().getResponse().getContentAsString();
        ApiResponse<Boolean> booleanApiResponse = BaseUtils.jsonToObject(contentAsString, Boolean.class);

        userRegister.andExpect(status().isOk());
        assertTrue(booleanApiResponse.isSuccess());
    }

    @Test
    public void emailVerificationTest() throws Exception {

        userRegisterDTO = new UserRegisterDTO("yusupov@gmail.com", "root");
        user = new User(userRegisterDTO.getEmail(), passwordEncoder.encode(userRegisterDTO.getPassword()));

        ResultActions email = mockMvc.perform(
                get("/api/auth/verification-email")
                        .param("email", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(BaseUtils.objectToJson(user.getEmail())))
        );

        email.andExpect(status().isOk());

    }

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Test
    public void signInSuccessfullyTest() throws Exception {

        loginDTO = new LoginDTO(adminEmail, adminPassword);

        ResultActions signIn = mockMvc.perform(
                post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(BaseUtils.objectToJson(loginDTO)))
        );

        String contentAsString = signIn.andReturn().getResponse().getContentAsString();
        ApiResponse<TokenDTO> apiResponse = BaseUtils.jsonToObject(contentAsString, TokenDTO.class);

        assertTrue(Objects.nonNull(apiResponse.getBody()));
        assertTrue(Objects.nonNull(apiResponse.getBody().getAccessToken()));
        assertTrue(Objects.nonNull(apiResponse.getBody().getRefreshToken()));

        tokenType = apiResponse.getBody().getTokenType();
        adminAccessToken = apiResponse.getBody().getAccessToken();
        adminRefreshToken = apiResponse.getBody().getRefreshToken();
        System.out.println(tokenType);
        System.out.println(adminAccessToken);
        System.out.println(adminRefreshToken);
    }

    @Test
    public void signInEmailNotFoundTest() throws Exception {

        loginDTO = new LoginDTO("yusupov2@gmail.com", "yusupov");

        ResultActions signIn = mockMvc.perform(
                post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(BaseUtils.objectToJson(loginDTO)))
        );

        String contentAsString = signIn.andReturn().getResponse().getContentAsString();
        ApiResponse<TokenDTO> apiResponse = BaseUtils.jsonToObject(contentAsString, TokenDTO.class);

        ApiErrorResponse apiErrorResponse =
                apiResponse.getErrors().get(0);
        assertEquals("Unauthorized error", apiErrorResponse.getFriendlyMessage());
    }

}
