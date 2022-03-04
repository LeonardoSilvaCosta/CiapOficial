package com.br.ciapoficial.network.api.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenResponse {

    private boolean success;
    private RefreshTokenResponseData data;

    @Getter
    @Setter
    @Builder
    public static class RefreshTokenResponseData {
        private String token;

    }


}