package uz.md.leetcode.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.md.leetcode.response.ApiErrorResponse;
import uz.md.leetcode.response.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@Component
@RequiredArgsConstructor
@Slf4j
public class MyEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        ApiResponse<List<ApiErrorResponse>> apiResponse = ApiResponse.failResponse(
                "Unauthorized error",
                "Unauthorized error",
                HttpStatus.UNAUTHORIZED.value(),
                request.getContextPath()
        );
        log.error("Full authentication is required to access this resource: {}", request.getRequestURI());

            String string = objectMapper.writeValueAsString(apiResponse);
            response.getWriter().write(string);
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

    }


}
