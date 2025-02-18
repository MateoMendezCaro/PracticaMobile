package com.funlam.loginmvp.service

import com.funlam.loginmvp.model.UserLoginRequest
import com.funlam.loginmvp.model.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun login(@Body request: UserLoginRequest): Call<UserLoginResponse>
}