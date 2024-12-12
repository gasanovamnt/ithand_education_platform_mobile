package com.example.ithand_education_platform_mobile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("posts") // Укажите эндпоинт
    Call<PostResponse> createPost(@Body PostRequest postRequest); // Метод для POST-запроса
}
