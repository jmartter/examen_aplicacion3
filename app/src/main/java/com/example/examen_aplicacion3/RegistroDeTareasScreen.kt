// RegistroDeTareasScreen.kt
package com.example.examen_aplicacion3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistroDeTareasScreen(modifier: Modifier = Modifier, firestore: FirebaseFirestore, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    var coste by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
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
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = prioridad,
            onValueChange = { prioridad = it },
            label = { Text("Prioridad") },
            modifier = Modifier.fillMaxWidth()
        )
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
                    if (coste.toDoubleOrNull() != null) {
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
                    } else {
                        errorMessage = "Coste debe ser un número válido"
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