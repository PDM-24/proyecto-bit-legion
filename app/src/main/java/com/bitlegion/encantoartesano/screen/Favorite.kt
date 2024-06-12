package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.MainViewModel
import com.bitlegion.encantoartesano.R
import com.bitlegion.encantoartesano.component.Header
import com.bitlegion.encantoartesano.ui.theme.Aqua
import com.bitlegion.encantoartesano.ui.theme.MainRed
import com.bitlegion.encantoartesano.ui.theme.grayWhite
import kotlinx.coroutines.CoroutineScope


data class FavProduct(
    val nombre: String,
    val descripcion: String,
    val precio: String,
)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FavUI(viewModel: MainViewModel, drawerState: DrawerState) {
    val localScope = rememberCoroutineScope()

    val favProduct = listOf(
        FavProduct("Nombre", "Descripcion", "Precio"),
        FavProduct("Nombre", "Descripcion", "Precio"),
        FavProduct("Nombre", "Descripcion", "Precio"),
    )

    Column(modifier = Modifier
        .background(color = grayWhite)
        .fillMaxSize()
    ) {
        Header(viewModel, drawerState = drawerState)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.bodySmall.copy(Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(){
            favProduct.forEach {producto ->
                FavProductCard(producto)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FavProductCard(producto: FavProduct) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.jarron), contentDescription = "Imagen del producto",
                modifier = Modifier
                    .width(110.dp)
                    .height(110.dp))
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 2.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(producto.nombre, style = MaterialTheme.typography.titleSmall.copy(Color.Black), fontWeight = FontWeight.Bold)
                Text(producto.descripcion, style = MaterialTheme.typography.bodySmall.copy(Color.Black))
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(producto.precio, style = MaterialTheme.typography.bodySmall.copy(Color.Black))
                    Spacer(modifier = Modifier.width(105.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(30.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Aqua, contentColor = MainRed)
                    ) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}