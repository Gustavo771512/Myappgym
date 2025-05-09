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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Rutina : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutinaScreen()
        }
    }
}

@Composable
fun RutinaScreen() {
    val context = LocalContext.current

    val ejercicios = listOf(
        Ejercicio("INCLINE PRESS", R.drawable.inclinado, "6/8 X 3", "Ajusta el banco a 30-45°, aprieta omóplatos, baja controlado y empuja con fuerza."),
        Ejercicio("PRESS BANCA", R.drawable.banca, "6/8 X 3", "Aprieta omóplatos y baja la barra al pecho controladamente."),
        Ejercicio("PECK DECK", R.drawable.peck_deck, "10/12 X 3", "Contrae el pecho al cerrar los brazos."),
        Ejercicio("TRÍCEPS POLEA", R.drawable.ext_triceps, "12 X 3", "Mantén los codos pegados al cuerpo y extiende completamente."),
        Ejercicio("FONDOS EN MÁQUINA", R.drawable.fondos, "10 X 3", "Baja controlado y sube extendiendo brazos.")
    )

    val cardio = Ejercicio("CARDIO EN MÁQUINA", R.drawable.cardio, "15 min", "Mantén ritmo constante, respira profundo.")

    var ejercicioActual by remember { mutableStateOf(0) }

    var series by remember {
        mutableStateOf(
            List(3) { SerieEditable("", "", "", "") }
        )
    }

    val mostrarCardio = ejercicioActual >= ejercicios.size
    val ejercicio = if (!mostrarCardio) ejercicios[ejercicioActual] else cardio

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Imagen del ejercicio
        Image(
            painter = painterResource(id = ejercicio.imagen),
            contentDescription = ejercicio.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = ejercicio.nombre, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = ejercicio.repeticiones, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = ejercicio.descripcion, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(32.dp))

        if (!mostrarCardio) {
            TablaRutinaEditable(series) { updated -> series = updated }
            Spacer(modifier = Modifier.height(32.dp))
        }

        if (!mostrarCardio) {
            Button(
                onClick = {
                    if (ejercicioActual < ejercicios.size) {
                        ejercicioActual++
                        series = List(3) { SerieEditable("", "", "", "") }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Siguiente ejercicio", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Terminar rutina", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TablaRutinaEditable(
    series: List<SerieEditable>,
    onUpdate: (List<SerieEditable>) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TableRowHeader()

        series.forEachIndexed { index, serie ->
            TableRowEditable(
                serie = serie,
                onSerieChange = { updatedSerie ->
                    val newList = series.toMutableList()
                    newList[index] = updatedSerie
                    onUpdate(newList)
                }
            )
        }
    }
}

@Composable
fun TableRowHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Serie", "Reps", "Rest", "Peso").forEach {
            Text(
                text = it,
                modifier = Modifier.weight(1f),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TableRowEditable(
    serie: SerieEditable,
    onSerieChange: (SerieEditable) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = serie.serie,
            onValueChange = { onSerieChange(serie.copy(serie = it)) },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = serie.reps,
            onValueChange = { onSerieChange(serie.copy(reps = it)) },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = serie.rest,
            onValueChange = { onSerieChange(serie.copy(rest = it)) },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = serie.peso,
            onValueChange = { onSerieChange(serie.copy(peso = it)) },
            modifier = Modifier.weight(1f)
        )
    }
}

data class Ejercicio(
    val nombre: String,
    val imagen: Int,
    val repeticiones: String,
    val descripcion: String
)

data class SerieEditable(
    val serie: String,
    val reps: String,
    val rest: String,
    val peso: String
)

@Preview(showBackground = true)
@Composable
fun RutinaPreview() {
    RutinaScreen()
}
