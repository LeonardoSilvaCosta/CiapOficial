package com.br.ciapoficial.network.api.service;

import com.br.ciapoficial.network.api.dto.LoginRequest;
import com.br.ciapoficial.network.api.dto.LoginResponse;
import com.br.ciapoficial.network.api.dto.PageServico;
import com.br.ciapoficial.network.api.dto.PageUsuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("auth")
    Call<LoginResponse> logar(@Body LoginRequest LoginRequest);

    @POST("auth/token")
    Call<LoginResponse> authorization(@Body String token);

    @POST("/api/auth/token")
    @FormUrlEncoded
    Call refreshToken(
            @Field("userId") String userId,
            @Field("refreshToken") String refreshToken);

    @GET("usuarios/paginados")
    Call<PageUsuario> atendidosFiltrados(
            @Query("keyword") String keyword);

    @GET("usuarios/paginados")
    Call<PageUsuario> paginarAtendidos(
            @Query("page") int page);

    @GET("servicos/paginados")
    Call<PageServico> servicosFiltrados(
            @Query("keyword") String keyword);

    @GET("servicos/paginados")
    Call<PageServico> paginarServicos(
            @Query("page") int page);

}
