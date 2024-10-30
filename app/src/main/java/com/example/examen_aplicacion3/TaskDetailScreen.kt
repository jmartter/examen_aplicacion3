package com.example.examen_aplicacion3

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen_aplicacion3.ui.theme.Examen_aplicacion3Theme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TaskDetailScreen(firestore: FirebaseFirestore, taskName: String?) {
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

    if (errorMessage != null) {
        Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
    } else {
        task?.let {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(text = "Nombre: ${it.nombre}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Descripción: ${it.descripcion}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Fecha: ${it.fecha}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Prioridad: ${it.prioridad}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Coste: ${it.coste}")
            }
        }
    }
}

