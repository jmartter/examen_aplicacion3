package com.example.examen_aplicacion3

data class Tarea(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val prioridad: String = "",
    val coste: Double = 0.0,
    val hecha: Boolean = false
)