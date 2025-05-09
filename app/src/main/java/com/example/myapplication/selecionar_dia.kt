package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme


class selecionar_dia : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                SelecionarDiaScreen()
            }
        }
    }
}


@Composable
fun SelecionarDiaScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "ELIGIR RUTINA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Imagen de Pecho
        RutinaOption(
            title = "Pecho",
            imageRes = R.drawable.inclinado,
            onClick = {
                val intent = Intent(context, dia::class.java)
                context.startActivity(intent)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Imagen de Espalda
        RutinaOption(
            title = "Espalda",
            imageRes = R.drawable.jalon_pecho, // Cambia esta imagen según corresponda
            onClick = {
                val intent = Intent(context, DiaEspalda::class.java)
                context.startActivity(intent)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        RutinaOption(
            title = "Pierna",
            imageRes = R.drawable.squat, // Cambia esta imagen según corresponda
            onClick = {
                val intent = Intent(context, DiaPierna::class.java)
                context.startActivity(intent)
            }
        )
    }
}


@Composable
fun RutinaOption(title: String, imageRes: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .size(200.dp)
                .clickable { onClick() }
        )
    }
}
