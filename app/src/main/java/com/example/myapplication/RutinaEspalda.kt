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

class RutinaEspalda : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutinaEspaldaScreen()
        }
    }
}

@Composable
fun RutinaEspaldaScreen() {
    val context = LocalContext.current

    val ejercicios = listOf(
        Ejercicio("JALONES AL PECHO ", R.drawable.jalon_pecho, "6/8 X 3", "Agarra la barra con un agarre amplio y tira hacia el pecho controladamente. Este ejercicio trabaja la parte superior de la espalda."),
        Ejercicio("REMOS CON BARRA ", R.drawable.remo_barra, "8/10 X 3", "Inclínate ligeramente hacia adelante y tira de la barra hacia el abdomen, concentrándote en los músculos de la espalda media."),
        Ejercicio("DOMINADAS ", R.drawable.pull_up, "8/10 X 3", "Agarra la barra con ambas manos y lleva los brazos hacia atrás, luego regresa lentamente. Ideal para trabajar la zona alta de la espalda."),
        Ejercicio("CURL BICEP ", R.drawable.curl_bicep, "10 X 3", "Tira con fuerza, llevando los codos hacia atrás, mantén la espalda recta para trabajar la parte media de la espalda."),
        Ejercicio("CURL MARTILLO ", R.drawable.curl_martillo, "8/10 X 3", "Con un agarre estrecho, tira la barra hacia el pecho y controla el movimiento. Focaliza en la parte baja de la espalda y los dorsales.")
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
            TablaRutinaEspaldaEditable(series) { updated -> series = updated }
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
fun TablaRutinaEspaldaEditable(
    series: List<SerieEditable>,
    onUpdate: (List<SerieEditable>) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TableRowHeaderEspalda()

        series.forEachIndexed { index, serie ->
            TableRowEditableEspalda(
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
fun TableRowHeaderEspalda() {
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
fun TableRowEditableEspalda(
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

data class EjercicioEspalda(
    val nombre: String,
    val imagen: Int,
    val repeticiones: String,
    val descripcion: String
)

data class SerieEditableEspalda(
    val serie: String,
    val reps: String,
    val rest: String,
    val peso: String
)

@Preview(showBackground = true)
@Composable
fun RutinaEspaldaPreview() {
    RutinaEspaldaScreen()
}
