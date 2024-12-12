package com.example.ithand_education_platform_mobile;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем экземпляр API
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Создаём объект запроса
        PostRequest postRequest = new PostRequest("Заголовок", "Текст поста", 1);

        // Отправляем POST-запрос
        Call<PostResponse> call = apiService.createPost(postRequest);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PostResponse postResponse = response.body();
                    Log.d("POST_RESPONSE", "ID: " + postResponse.getId() +
                            ", Title: " + postResponse.getTitle());
                } else {
                    Log.e("POST_RESPONSE", "Ответ пустой или не успешный");
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("POST_RESPONSE", "Ошибка: " + t.getMessage());
            }
        });
    }
}