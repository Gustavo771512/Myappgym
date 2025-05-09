package com.example.myapplication

import android.content.Context
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

    var peso by remember { mutableStateOf(sharedPreferences.getString("peso", "")) }
    var edad by remember { mutableStateOf(sharedPreferences.getString("edad", "")) }
    var altura by remember { mutableStateOf(sharedPreferences.getString("altura", "")) }
    var meta by remember { mutableStateOf(sharedPreferences.getString("meta", "Bajar de peso") ?: "Bajar de peso") }
    var genero by remember { mutableStateOf(sharedPreferences.getString("genero", "Hombre") ?: "Hombre") }

    var isEditing by remember { mutableStateOf(false) }

    // Cambiar imagen según el género
    val imageResource = if (genero == "Mujer") {
        R.drawable.silueta_mujer // Asegúrate de tener esta imagen en res/drawable
    } else {
        R.drawable.silueta // La imagen para "Hombre"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "TU\nINFORMACIÓN hola",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Aquí se usa la variable imageResource para cambiar la imagen
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Silueta",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = peso ?: "",
                    onValueChange = { peso = it },
                    label = { Text("Peso (kg)") },
                    enabled = isEditing,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = edad ?: "",
                    onValueChange = { edad = it },
                    label = { Text("Edad (años)") },
                    enabled = isEditing,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = altura ?: "",
                    onValueChange = { altura = it },
                    label = { Text("Altura (m)") },
                    enabled = isEditing,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                // Dropdown para Género
                var generoExpanded by remember { mutableStateOf(false) }
                val generoOptions = listOf("Hombre", "Mujer")
                Box {
                    Button(
                        onClick = { generoExpanded = true },
                        enabled = isEditing,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text("Género: $genero")
                    }
                    DropdownMenu(
                        expanded = generoExpanded,
                        onDismissRequest = { generoExpanded = false }
                    ) {
                        generoOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    genero = option
                                    generoExpanded = false
                                }
                            )
                        }
                    }
                }

                // Dropdown para Meta
                var metaExpanded by remember { mutableStateOf(false) }
                val metaOptions = listOf("Bajar de peso", "Aumentar masa muscular", "Definir")
                Box {
                    Button(
                        onClick = { metaExpanded = true },
                        enabled = isEditing,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text("Meta: $meta")
                    }
                    DropdownMenu(
                        expanded = metaExpanded,
                        onDismissRequest = { metaExpanded = false }
                    ) {
                        metaOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    meta = option
                                    metaExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                isEditing = !isEditing
                if (!isEditing) {
                    // Guardar la información
                    with(sharedPreferences.edit()) {
                        putString("peso", peso)
                        putString("edad", edad)
                        putString("altura", altura)
                        putString("meta", meta)
                        putString("genero", genero)
                        apply()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text(
                if (isEditing) "Guardar Información" else "Editar Información",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = {
                context.startActivity(Intent(context, calendario::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text("Calendario", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, marca::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text("Tus Marcas", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                context.startActivity(Intent(context, selecionar_dia::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text("Empezar Rutina", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, TuPr::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text("Tu Pr", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }


    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
