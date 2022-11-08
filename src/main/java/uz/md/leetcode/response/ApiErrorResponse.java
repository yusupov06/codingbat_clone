package uz.md.leetcode.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApiErrorResponse {
    private String friendlyMessage;
    private String developerMessage;
    private Map<String, Object> errorFields;
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
    private String requestPath;

    public ApiErrorResponse(String friendlyMessage, String developerMessage, Timestamp timestamp, String requestPath) {
        this.friendlyMessage = friendlyMessage;
        this.developerMessage = developerMessage;
        this.timestamp = timestamp;
        this.requestPath = requestPath;
    }

}
