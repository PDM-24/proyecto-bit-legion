package com.bitlegion.encantoartesano.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitlegion.encantoartesano.ui.theme.ColorCards



@Composable
fun MyCardMenu(text: String, imageResource: Int, imageSize: Dp = 45.dp) {
    Card(colors= CardDefaults.cardColors(containerColor = ColorCards),
        modifier= Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .size(width = 50.dp, height = 50.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Icono",
                modifier = Modifier.size(imageSize)
                    .padding(8.dp)
            )
            Text(text = text, fontWeight = FontWeight.Bold)
        }

    }
}
