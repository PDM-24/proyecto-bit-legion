package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitlegion.encantoartesano.ui.theme.BgActiveUsers


@Composable
fun ActiveUsers(modifier: Modifier = Modifier) {

Column(modifier = Modifier
    .background(color = BgActiveUsers)
    .fillMaxWidth()
    .fillMaxHeight(),
    horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = "Usuarios Activos", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(30.dp))
    LazyColumn(modifier= Modifier
        ){
        items(userList) {ActiveUsersData ->

            MyCardUser(
                name = ActiveUsersData.name,
                password = ActiveUsersData.password ,
                numProductos = ActiveUsersData.numProductos,
                age = ActiveUsersData.age)
        }
    }
 }


}










@Preview(showBackground = true)
@Composable
fun ActiveUsersPreview(modifier: Modifier = Modifier) {

    ActiveUsers()
    
}