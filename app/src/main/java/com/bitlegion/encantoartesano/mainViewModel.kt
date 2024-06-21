package com.bitlegion.encantoartesano

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {


    private val _userRole = mutableStateOf<String?>(null)
    val userRole: State<String?> get() = _userRole

    private val _productId = mutableStateOf<String?>(null)
    val productId: State<String?> get() = _productId

    fun updateUserRole(newRole: String?) {
        _userRole.value = newRole
    }

    fun updateProductId(newRole: String?) {
        _userRole.value = newRole
    }
    // Utiliza el viewModelScope para corutinas
    val coroutineScope: CoroutineScope = viewModelScope


    // Define el DrawerState en el ViewModel
    val drawerState = DrawerState(DrawerValue.Closed)

    fun openDrawer() {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    fun closeDrawer() {
        coroutineScope.launch {
            drawerState.close()
        }
    }


}