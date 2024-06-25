package com.bitlegion.encantoartesano.Api

data class User(
    val username: String,
    val password: String,
    val edad: Int,
    val profilePicture: String = ""
) {
    val _id: String = ""
}

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val userId: String,
    val userRol: String
)

data class UserWithState(
    val _id: String?,
    val username: String,
    val password: String?,
    val edad: Int,
    val profilePicture: String = "",
    var userState: Boolean
)



