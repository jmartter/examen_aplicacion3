package com.example.examen_aplicacion3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.examen_aplicacion3.ui.theme.Examen_aplicacion3Theme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects

@Composable
fun TaskListScreen(firestore: FirebaseFirestore, navController: NavController) {
    var tasks by remember { mutableStateOf(listOf<Tarea>()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Tarea?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Cargar tareas de Firestore
    LaunchedEffect(Unit) {
        firestore.collection("tareas").get().addOnSuccessListener { result ->
            tasks = result.toObjects()
        }.addOnFailureListener { exception ->
            errorMessage = "Error loading tasks: ${exception.message}"
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        tasks.forEach { task ->
            Text(
                text = task.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        selectedTask = task
                        showDialog = true // Mostrar el diálogo al hacer clic en la tarea
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    // Mostrar mensaje de error si ocurre
    errorMessage?.let {
        Text(text = it, color = MaterialTheme.colorScheme.error)
    }

    // Dialogo para opciones: ver detalles o borrar
    if (showDialog && selectedTask != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Opciones de tarea") },
            text = { Text("¿Qué te gustaría hacer con la tarea ${selectedTask?.nombre}?") },
            confirmButton = {
                Button(onClick = {
                    navController.navigate("taskDetail/${selectedTask?.nombre}")
                    showDialog = false
                }) {
                    Text("Ver detalles")
                }
            },
            dismissButton = {
                Button(onClick = {
                    if (!isLoading) {
                        isLoading = true
                        selectedTask?.let { task ->
                            firestore.collection("tareas").document(task.id)
                                .delete()
                                .addOnSuccessListener {
                                    tasks = tasks.filter { it.id != task.id }
                                    showDialog = false
                                    isLoading = false
                                }
                                .addOnFailureListener { exception ->
                                    errorMessage = "Error deleting task: ${exception.message}"
                                    showDialog = false
                                    isLoading = false
                                }
                        }
                    }
                }) {
                    Text("Borrar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    Examen_aplicacion3Theme {
        val navController = rememberNavController()
        TaskListScreen(firestore = FirebaseFirestore.getInstance(), navController = navController)
    }
}