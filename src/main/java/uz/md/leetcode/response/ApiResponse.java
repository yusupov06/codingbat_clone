package uz.md.leetcode.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/10:37 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApiResponse<T> implements Serializable {
    private T body;

    private String message;
    private Integer status;
    private Integer total;
    private boolean success;
    private List<ApiErrorResponse> errors;

    public ApiResponse() {
        success = true;
    }

    public ApiResponse(List<ApiErrorResponse> errors, Integer code) {
        this.errors = errors;
        this.total = errors.size();
        this.success = false;
        this.status = code;
    }

    public ApiResponse(String message, T body) {
        this.message = message;
        this.body = body;
        this.success = true;
    }

    public static ApiResponse<List<ApiErrorResponse>> failResponse(List<ApiErrorResponse> errors, Integer code) {
        return new ApiResponse<>(errors, code);
    }

    public static ApiResponse<List<ApiErrorResponse>> failResponse(String friendlyMsg, String developerMsg, Integer code, String path) {
        List<ApiErrorResponse> errorDataList = List.of(new ApiErrorResponse(
                friendlyMsg,
                developerMsg,
                Timestamp.valueOf(LocalDateTime.now()),
                path));
        return failResponse(errorDataList, code);
    }

    public static <T> ApiResponse<T> successResponse(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> successResponse( T data) {
        return new ApiResponse<>(null, data);
    }

    public static <T> ApiResponse<T> successResponse() {
        return new ApiResponse<>();
    }

    public static <T> ApiResponse<T> successResponse(String message) {
        return new ApiResponse<>(message, null);
    }

}
