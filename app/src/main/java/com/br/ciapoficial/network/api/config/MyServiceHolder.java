package com.br.ciapoficial.network.api.config;

import androidx.annotation.Nullable;

import com.br.ciapoficial.network.api.service.ApiService;

public class MyServiceHolder {

    ApiService apiService = null;

    @Nullable
    public ApiService get() {
        return apiService;
    }

    public void set(ApiService apiService) {
        this.apiService = apiService;
    }
}
