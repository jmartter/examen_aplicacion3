package com.example.examen_aplicacion3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen_aplicacion3.ui.theme.Examen_aplicacion3Theme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects

@Composable
fun TaskListScreen(firestore: FirebaseFirestore) {
    var tasks by remember { mutableStateOf(listOf<Tarea>()) }

    LaunchedEffect(Unit) {
        firestore.collection("tareas").get().addOnSuccessListener { result ->
            tasks = result.toObjects()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        tasks.forEach { task ->
            Text(text = task.nombre)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    Examen_aplicacion3Theme {
        TaskListScreen(firestore = FirebaseFirestore.getInstance())
    }
}