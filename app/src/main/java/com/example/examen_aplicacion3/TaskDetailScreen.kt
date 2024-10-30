package com.example.examen_aplicacion3

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.examen_aplicacion3.ui.theme.Examen_aplicacion3Theme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TaskDetailScreen(firestore: FirebaseFirestore, taskName: String?, navController: NavController) {
    var task by remember { mutableStateOf<Tarea?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(taskName) {
        if (taskName == null) {
            errorMessage = "Invalid task name"
            return@LaunchedEffect
        }

        firestore.collection("tareas").whereEqualTo("nombre", taskName).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    task = documents.documents[0].toObject(Tarea::class.java)
                } else {
                    errorMessage = "Task not found"
                }
            }
            .addOnFailureListener { exception ->
                errorMessage = "Error fetching task: ${exception.message}"
                Log.e("TaskDetailScreen", "Error fetching task", exception)
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        // Título de la pantalla
        Text(text = "Tarea", style = MaterialTheme.typography.headlineMedium)

        if (errorMessage != null) {
            Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        } else {
            task?.let {
                Column {
                    // Nombre de la tarea
                    Text(
                        text = it.nombre,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Descripción
                    Text(text = "Descripción: ${it.descripcion}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Fecha
                    Text(text = "Para el: ${it.fecha}")
                    Spacer(modifier = Modifier.height(8.dp))

                    // Prioridad
                    Text(
                        text = "Prioridad: ${it.prioridad}",
                        color = if (it.prioridad == "Alta") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Coste
                    Text(text = "Tiene un coste de: €${it.coste}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Button(
            onClick = { navController.navigate("taskList") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskDetailScreen() {
    Examen_aplicacion3Theme {
        // Utilizar rememberNavController para crear un controlador de navegación simulado
        val navController = rememberNavController()
        TaskDetailScreen(FirebaseFirestore.getInstance(), "Tarea de Ejemplo", navController)
    }
}
