package com.example.examen_aplicacion3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examen_aplicacion3.ui.theme.Examen_aplicacion3Theme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        setContent {
            Examen_aplicacion3Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "registroDeTareas") {
                    composable("registroDeTareas") {
                        RegistroDeTareasScreen(firestore = firestore, navController = navController)
                    }
                    composable("taskList") {
                        TaskListScreen(firestore = firestore)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    Examen_aplicacion3Theme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "registroDeTareas") {
            composable("registroDeTareas") {
                RegistroDeTareasScreen(firestore = FirebaseFirestore.getInstance(), navController = navController)
            }
            composable("taskList") {
                TaskListScreen(firestore = FirebaseFirestore.getInstance())
            }
        }
    }
}