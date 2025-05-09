package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TuPr : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            tuPrScreen()
        }
    }
}

@Composable
fun tuPrScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

    // Obtener el peso, altura y género almacenados en SharedPreferences sin valores predeterminados
    val peso = sharedPreferences.getString("peso", null)
    val altura = sharedPreferences.getString("altura", null)
    val genero = sharedPreferences.getString("genero", null)

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
                text = "TU PRONÓSTICO",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Mostrar imagen según el género
            if (genero != null) {
                val imageRes = if (genero.lowercase() == "mujer") {
                    R.drawable.silueta_mujer // Cambia esto con el nombre real de la imagen en res/drawable
                } else {
                    R.drawable.silueta // Cambia esto con el nombre real de la imagen en res/drawable
                }
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Imagen de género",
                    modifier = Modifier.size(350.dp)
                )
            }



            Spacer(modifier = Modifier.height(16.dp))

            // Verificar si peso y altura existen para mostrar la información
            if (peso != null && altura != null) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Pesas $peso kg   y",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = "Mides $altura",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                // Si también se encuentra el género, calcular el ejercicio
                if (genero != null) {
                    val pesoNumerico = peso.filter { it.isDigit() }.toIntOrNull()
                    if (pesoNumerico != null) {
                        // Calcular los valores ajustados de los ejercicios
                        val ejercicios = listOf("Press Banca", "Curl Biceps", "Sentadilla", "Peso Muerto")
                        val valoresEjercicios = ejercicios.map { ejercicio ->
                            val factor = when (genero.lowercase()) {
                                "hombre" -> when (ejercicio) {
                                    "Press Banca" -> 1.1
                                    "Curl Biceps" -> 0.3
                                    "Sentadilla" -> 1.3
                                    "Peso Muerto" -> 1.3
                                    else -> 1.0
                                }
                                "mujer" -> when (ejercicio) {
                                    "Press Banca" -> 0.8
                                    "Curl Biceps" -> 0.2
                                    "Sentadilla" -> 1.0
                                    "Peso Muerto" -> 1.0
                                    else -> 1.0
                                }
                                else -> 1.0
                            }
                            ejercicio to (pesoNumerico * factor)
                        }

                        // Mostrar los valores ajustados de cada ejercicio
                        Spacer(modifier = Modifier.height(16.dp))
                        valoresEjercicios.forEach { (ejercicio, valorAjustado) ->
                            Text(
                                text = "$ejercicio Esperado: ${valorAjustado.toInt()} kg",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0047AB),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "Datos incompletos. Por favor, completa tu perfil.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))



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
