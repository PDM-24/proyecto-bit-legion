package com.bitlegion.encantoartesano.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bitlegion.encantoartesano.Api.ApiClient
import com.bitlegion.encantoartesano.Api.UserWithState
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.ui.theme.BgActiveUsers
import com.bitlegion.encantoartesano.ui.theme.ColorCards
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun BlockedUsers(modifier: Modifier = Modifier) {
    var users by remember { mutableStateOf(emptyList<UserWithState>()) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Llama a la API para obtener los usuarios activos
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response: Response<List<UserWithState>> = ApiClient.apiService.getBlockedUsers()
                if (response.isSuccessful) {
                    users = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar usuarios: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = BgActiveUsers)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Usuarios bloqueados", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(30.dp))
        LazyColumn(modifier = Modifier) {
            items(users) { user ->
                BlockedCardUser(
                    user = user,
                    onToggleUserState = { userId ->
                        coroutineScope.launch {
                            try {
                                val response = ApiClient.apiService.updateState(""+user._id)
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Estado actualizado exitosamente", Toast.LENGTH_SHORT).show()
                                    users = users.map { if (it.username == user.username) it.copy(userState = !it.userState) else it }
                                } else {
                                    Toast.makeText(context, "Error al actualizar el estado", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BlockedCardUser(user: UserWithState, onToggleUserState: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        onToggleStateDialog2(
            onConfirm = {
                onToggleUserState(user.username)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = ColorCards),
        modifier = Modifier
            .padding(7.dp)
            .size(width = 320.dp, height = 150.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.profile_icon), contentDescription = "Perfil", Modifier.size(55.dp))
            Column(modifier = Modifier.fillMaxSize(1f)) {
                Text(text = "Usuario: ${user.username}", fontSize = 16.sp)
                Text(text = "Edad: ${user.edad}", fontSize = 16.sp)
                Text(text = "Estado: ${if (user.userState) "Activo" else "Bloqueado"}", fontSize = 16.sp)

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(text = if (user.userState) "Bloquear" else "Desbloquear")
                }
            }
        }
    }
}

@Composable
fun onToggleStateDialog2(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "¿Estás seguro de cambiar el estado de este usuario?", color = Color.Black, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Confirmar", color = Color.White, fontSize = 16.sp)
                    }
                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Cancelar", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BlockedUsersPreview(modifier: Modifier = Modifier) {
    ActiveUsers()
}
