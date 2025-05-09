package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.setContent;
import androidx.compose.foundation.Image;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.Button;
import androidx.compose.material3.Text;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.LocalContext;
import androidx.compose.ui.res.painterResource;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.ui.unit.dp;
import androidx.compose.ui.unit.sp;


class dia : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            diaScreen()
        }
    }
}


@Composable
fun diaScreen() {
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize().padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(29.dp))
        Text(text = "TU", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(text = "RUTINA DE HOY", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(29.dp))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RutinaItem(R.drawable.inclinado, "6/8 X 3")
                RutinaItem(R.drawable.banca, "8 X 4")
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RutinaItem(R.drawable.peck_deck, "12 X 4")
                RutinaItem(R.drawable.ext_triceps, "12 X 3")
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RutinaItem(R.drawable.fondos, "8 X 3")
                RutinaItem(R.drawable.cardio, "30 m")
            }
        }
        Spacer(modifier = Modifier.height(29.dp))
        Button(
            onClick = {
                val intent = Intent(context, Rutina::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(14.dp)
        ) {
            Text(text = "INICIA", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun RutinaItem(imageRes: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(14.dp)) {
        Image(painter = painterResource(id = imageRes), contentDescription = null, modifier = Modifier.size(144.dp))
        Text(text = text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}


@Preview(showBackground = true)
@Composable
fun diaPreview() {
    diaScreen()
}
