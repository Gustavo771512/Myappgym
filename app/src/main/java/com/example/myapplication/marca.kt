package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class marca : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            marcaScreen()
        }
    }
}

fun guardarValores(context: Context, valores: List<Int>) {
    val sharedPreferences = context.getSharedPreferences("MarcaValores", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        valores.forEachIndexed { index, value ->
            putInt("valor_$index", value)
        }
        apply()
    }
}

fun cargarValores(context: Context): MutableList<Int> {
    val sharedPreferences = context.getSharedPreferences("MarcaValores", Context.MODE_PRIVATE)
    return MutableList(8) { index ->
        sharedPreferences.getInt("valor_$index", 0)
    }
}

@Composable
fun marcaScreen() {
    val context = LocalContext.current
    val valores = remember { mutableStateListOf(50, 80, 100, 120, 120, 140, 180, 200) }

    // Cargar valores guardados si están disponibles
    LaunchedEffect(context) {
        val valoresGuardados = cargarValores(context)
        valores.clear()
        valores.addAll(valoresGuardados)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TU HISTORIAL",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            val ejercicios = listOf("Banca", "Curl Bicep", "Sentadilla", "P.M")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Mes 1", fontSize = 18.sp)
                Text("Mes 2", fontSize = 18.sp)
            }

            Column {
                for (i in 0 until 4) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextField(
                            value = valores[2 * i].takeIf { it > 0 }?.toString() ?: "",
                            onValueChange = { newValue ->
                                valores[2 * i] = newValue.toIntOrNull() ?: 0
                                guardarValores(context, valores)
                            },
                            label = { Text(ejercicios[i]) },
                            modifier = Modifier.width(100.dp)
                        )
                        TextField(
                            value = valores[2 * i + 1].takeIf { it > 0 }?.toString() ?: "",
                            onValueChange = { newValue ->
                                valores[2 * i + 1] = newValue.toIntOrNull() ?: 0
                                guardarValores(context, valores)
                            },
                            label = { Text(ejercicios[i]) },
                            modifier = Modifier.width(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            barChart(valores)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                Text(text = "Inicio", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun barChart(valores: List<Int>) {
    val ejercicios = listOf("CURL BICEP", "BANCA", "SENTADILLA", "P.M")
    val marcas = listOf(0, 50, 100, 150, 200)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            marcas.reversed().forEach { marca ->
                Text(
                    text = "$marca",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                marcas.forEach { _ ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray.copy(alpha = 0.5f))
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                for (i in 0 until 4) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(valores[2 * i].dp)
                                    .background(Color.Blue, shape = RoundedCornerShape(4.dp))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(valores[2 * i + 1].dp)
                                    .background(Color.Cyan, shape = RoundedCornerShape(4.dp))
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = ejercicios[i],
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(70.dp)
                        )
                    }
                }
            }
        }
    }
}
