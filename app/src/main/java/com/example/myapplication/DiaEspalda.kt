package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DiaEspalda : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaEspaldaScreen()
        }
    }
}

@Composable
fun DiaEspaldaScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(29.dp))
        Text(text = "TU", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(text = "RUTINA DE ESPALDA", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(29.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RutinaEspaldaItem(R.drawable.jalon_pecho, "8/10 X 4")
                RutinaEspaldaItem(R.drawable.remo_barra, "10 X 3")
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RutinaEspaldaItem(R.drawable.pull_up, "8 X 3")
                RutinaEspaldaItem(R.drawable.curl_bicep, "12 X 4")
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RutinaEspaldaItem(R.drawable.curl_martillo, "8 X 3")
                RutinaEspaldaItem(R.drawable.cardio, "30 m")
            }
        }
        Spacer(modifier = Modifier.height(29.dp))
        Button(
            onClick = {
                val intent = Intent(context, RutinaEspalda::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(14.dp)
        ) {
            Text(text = "INICIA", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RutinaEspaldaItem(imageRes: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(14.dp)) {
        Image(painter = painterResource(id = imageRes), contentDescription = null, modifier = Modifier.size(144.dp))
        Text(text = text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun DiaEspaldaPreview() {
    DiaEspaldaScreen()
}
