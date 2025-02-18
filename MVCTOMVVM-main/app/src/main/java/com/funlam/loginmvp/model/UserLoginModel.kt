package com.funlam.loginmvp.model

data class UserLoginRequest(val username: String, val password: String)
data class UserLoginResponse(val success: Boolean, val token: String?)