package com.example.ithand_education_platform_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView forgotPasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // Получаем экземпляр API
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ResetPasswordActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostRequest postRequest = new PostRequest(editTextLogin.getText().toString().trim(), editTextPassword.getText().toString().trim(), 3);
                Call<PostResponse> call = apiService.createPost(postRequest);
                call.enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            PostResponse postResponse = response.body();
                            Log.d("POST_RESPONSE", "ID: " + postResponse.getId() +
                                    ", Title: " + postResponse.getTitle());
                            Toast.makeText(MainActivity.this, postResponse.getTitle() + postResponse.getBody(), Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("POST_RESPONSE", "Ответ пустой или не успешный");
                            Toast.makeText(MainActivity.this, "Ничего не произошло", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        Log.e("POST_RESPONSE", "Ошибка: " + t.getMessage());
                        Toast.makeText(MainActivity.this, "не работает", Toast.LENGTH_LONG);
                    }
                });

            }
        });
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
                    Toast.makeText(MainActivity.this, postResponse.getTitle() + postResponse.getBody(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e("POST_RESPONSE", "Ответ пустой или не успешный");
                    Toast.makeText(MainActivity.this, "Ничего не произошло", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("POST_RESPONSE", "Ошибка: " + t.getMessage());
                Toast.makeText(MainActivity.this, "не работает", Toast.LENGTH_LONG);
            }
        });
    }
    private void initViews(){
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
    }
}