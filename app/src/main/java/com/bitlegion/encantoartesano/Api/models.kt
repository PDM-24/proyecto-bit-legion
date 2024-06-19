package com.bitlegion.encantoartesano.Api

data class User(
    val username: String,
    val password: String,
    val edad: Int
)

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val userId: String,
    val userRol: String
)



