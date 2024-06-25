package com.bitlegion.encantoartesano

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.LoginRequest
import com.bitlegion.encantoartesano.Api.LoginResponse
import com.bitlegion.encantoartesano.Api.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val response: Response<LoginResponse> = ApiClient.apiService.loginUser(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    TokenManager.saveToken(loginResponse.token)
                    onResult(true)
                } ?: run {
                    onResult(false)
                }
            } else {
                onResult(false)
            }
        }
    }
}
