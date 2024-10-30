package com.example.examen_aplicacion3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.examen_aplicacion3.Tarea
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegistroDeTareasScreen(modifier: Modifier = Modifier, firestore: FirebaseFirestore, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("Baja") } // Valor inicial predeterminado
    var coste by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) } // Controla la expansión del menú desplegable

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = Date()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Franja azul con el título
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF2196F3)) // Color azul
        ) {
            Text(
                text = "Nueva Tarea",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido principal
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (dd/MM/yyyy)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Selección de prioridad con un menú desplegable
        Text(text = "Prioridad: $prioridad")
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar Prioridad")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Baja", "Media", "Alta").forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        prioridad = option
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = coste,
            onValueChange = { coste = it },
            label = { Text("Coste") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val parsedDate = dateFormat.parse(fecha)
                    if (parsedDate == null || parsedDate.before(currentDate)) {
                        errorMessage = "Fecha inválida o pasada"
                    } else if (coste.toDoubleOrNull() == null) {
                        errorMessage = "Coste debe ser un número válido"
                    } else {
                        val tarea = Tarea(
                            id = firestore.collection("tareas").document().id,
                            nombre = nombre,
                            descripcion = descripcion,
                            fecha = fecha,
                            prioridad = prioridad,
                            coste = coste.toDouble(),
                            hecha = false // Por defecto, las tareas se crean como pendientes
                        )
                        firestore.collection("tareas")
                            .document(tarea.id)
                            .set(tarea)
                            .addOnSuccessListener {
                                navController.navigate("taskList")
                            }
                            .addOnFailureListener {
                                errorMessage = "Error al guardar la tarea"
                            }
                    }
                }
            ) {
                Text("Añadir Tarea")
            }
            Button(
                onClick = {
                    navController.navigate("taskList")
                }
            ) {
                Text("Ver Lista de Tareas")
            }
        }
    }
}
