package uz.md.leetcode.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.md.leetcode.domain.User;
import uz.md.leetcode.exceptions.RestException;
import uz.md.leetcode.repository.UserRepository;
import uz.md.leetcode.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;


@Component
public class BaseUtils {


    public static final String PATH = "/home/muhammadqodir/MyJava/My projects/My projects/Spring boot/CodingCompiler/src/main/java/uz/md/codingcompiler/solution/";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGES_DASHES = Pattern.compile("(^-|-$)");

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final UserRepository userRepository;

    public BaseUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public static String makeUrl(String input) {
        if (Objects.isNull(input))
            throw new IllegalArgumentException("For make url given input must not be null");

        String nonWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nonWhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        slug = EDGES_DASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase();
    }

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return Optional.of(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static User getAuthorizedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    private byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest(input);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x",
                    b));
        }
        return sb.toString();
    }

    public String encode(@NonNull String codeForEncoding) {
        byte[] digest = digest(codeForEncoding.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }


    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public static <T> ApiResponse<T> jsonToObject(String json, Class<T> contentClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, contentClass);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }




//    public User getCurrentUser(){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username;
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        return userRepository.findByUsername(username).orElseThrow(() -> {
//            throw new RestException("USER NOT FOUND", HttpStatus.NOT_FOUND);
//        });
//    }


}

