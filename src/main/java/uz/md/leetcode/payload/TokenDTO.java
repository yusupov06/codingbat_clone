package uz.md.leetcode.payload;;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    private final String tokenType = "Bearer";

}
