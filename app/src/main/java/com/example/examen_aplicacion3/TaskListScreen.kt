package com.example.examen_aplicacion3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    var showHechas by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        firestore.collection("tareas").get().addOnSuccessListener { result ->
            tasks = result.toObjects()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
       Text(text = "Lista de Tareas", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))
        val filteredTasks = if (showHechas) tasks.filter { it.hecha } else tasks.filter { !it.hecha }

        filteredTasks.forEach { task ->
            Text(
                text = task.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("taskDetail/${task.nombre}")
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { showHechas = false }) {
                Text("Pendientes")
            }
            Button(onClick = { showHechas = true }) {
                Text("Hechas")
            }
        }
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