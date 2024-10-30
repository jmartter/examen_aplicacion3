package com.example.examen_aplicacion3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.examen_aplicacion3.ui.theme.Examen_aplicacion3Theme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistroDeTareasScreen(modifier: Modifier = Modifier, firestore: FirebaseFirestore, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    var coste by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val tarea = Tarea(nombre, descripcion, fecha, prioridad, coste.toDouble())
                    firestore.collection("tareas")
                        .add(tarea)
                        .addOnSuccessListener {
                            navController.navigate("taskList")
                        }
                        .addOnFailureListener {
                            // Handle failure
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

@Preview(showBackground = true)
@Composable
fun RegistroDeTareasScreenPreview() {
    Examen_aplicacion3Theme {
        RegistroDeTareasScreen(firestore = FirebaseFirestore.getInstance(), navController = rememberNavController())
    }
}