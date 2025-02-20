package com.funlam.loginmvp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.funlam.loginmvp.model.UserLoginRequest
import com.funlam.loginmvp.model.UserLoginResponse
import com.funlam.loginmvp.service.LoginService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel : ViewModel() {

    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var loginSuccess by mutableStateOf<Boolean?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://example.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val loginService: LoginService = retrofit.create(LoginService::class.java)

    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Campos vacíos"
            return
        }

        isLoading = true
        errorMessage = null

        val request = UserLoginRequest(username, password)
        loginService.login(request).enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                isLoading = false
                if (response.isSuccessful && response.body()?.success == true) {
                    loginSuccess = true
                } else {
                    errorMessage = "Credenciales inválidas"
                    loginSuccess = false
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                isLoading = false
                errorMessage = "Error de conexión"
                loginSuccess = false
            }
        })
    }
}