# Examen Aplicacion 3

## Enlace al Repositorio
[Repositorio en GitHub](https://github.com/jmartter/examen_aplicacion3.git)

## Descripción

Esta aplicación es un gestor de tareas desarrollado en Kotlin utilizando Jetpack Compose para la interfaz de usuario y Firebase Firestore para el almacenamiento de datos. Permite a los usuarios registrar nuevas tareas, ver una lista de tareas pendientes y completadas, y ver los detalles de cada tarea.

## Estructura del Proyecto

El proyecto está organizado en los siguientes archivos y clases principales:

### `MainActivity.kt`

Esta es la actividad principal que configura la navegación de la aplicación. Utiliza `NavHost` para definir las rutas de navegación entre las diferentes pantallas de la aplicación.

### `RegistroDeTareasScreen.kt`

Esta pantalla permite a los usuarios registrar nuevas tareas. Incluye campos para ingresar el nombre, descripción, fecha, prioridad y coste de la tarea. Al guardar la tarea, se almacena en Firestore.

### `TaskListScreen.kt`

Esta pantalla muestra una lista de tareas. Los usuarios pueden filtrar las tareas por pendientes o completadas. Al hacer clic en una tarea, se muestra un diálogo con opciones para ver detalles, borrar o marcar la tarea como hecha.

### `TaskDetailScreen.kt`

Esta pantalla muestra los detalles de una tarea específica. Incluye el nombre, descripción, fecha, prioridad y coste de la tarea.

### `Tarea.kt`

Esta es la clase de datos que representa una tarea. Incluye propiedades como `id`, `nombre`, `descripcion`, `fecha`, `prioridad`, `coste` y `hecha`.

## Funcionalidades

- **Registrar Tareas**: Permite a los usuarios registrar nuevas tareas con detalles como nombre, descripción, fecha, prioridad y coste.
- **Ver Lista de Tareas**: Muestra una lista de tareas pendientes y completadas. Los usuarios pueden filtrar las tareas por su estado.
- **Ver Detalles de Tareas**: Permite a los usuarios ver los detalles de una tarea específica.
- **Borrar Tareas**: Permite a los usuarios borrar una tarea.
- **Marcar Tareas como Hechas**: Permite a los usuarios marcar una tarea como completada.

## Navegación

La navegación entre las pantallas se maneja utilizando `NavController` y `NavHost`. Las rutas principales son:

- `registroDeTareas`: Pantalla para registrar nuevas tareas.
- `taskList`: Pantalla que muestra la lista de tareas.
- `taskDetail/{taskName}`: Pantalla que muestra los detalles de una tarea específica.

## Firebase Firestore

La aplicación utiliza Firebase Firestore para almacenar y recuperar datos de las tareas. Las operaciones principales incluyen:

- **Guardar Tarea**: Al registrar una nueva tarea, se guarda en la colección `tareas` de Firestore.
- **Cargar Tareas**: Se cargan las tareas desde Firestore y se muestran en la lista de tareas.
- **Borrar Tarea**: Se elimina una tarea de Firestore.
- **Actualizar Tarea**: Se actualiza el estado de una tarea en Firestore.
