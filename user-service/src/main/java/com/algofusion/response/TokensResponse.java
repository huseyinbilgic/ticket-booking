package com.algofusion.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokensResponse {
    private String accessToken;
    private String refreshToken;
}
