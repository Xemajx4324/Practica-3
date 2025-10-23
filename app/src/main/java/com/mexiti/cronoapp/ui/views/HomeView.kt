package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.ui.components.TaskCard // CAMBIO
import com.mexiti.cronoapp.ui.components.FloatButton
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.viewmodel.TaskDataViewModel // CAMBIO
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, dataVM: TaskDataViewModel) { // CAMBIO
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Gestor de Tareas") }, // CAMBIO
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")
            }
        }
    ) {
        ContentHomeView(it = it, navController, dataVM)
    }
}

@Composable
fun ContentHomeView(
    it: PaddingValues,
    navController: NavController,
    dataVM: TaskDataViewModel // CAMBIO
) {
    Column(
        modifier = Modifier.padding(it)
    ) {
        val dataList by dataVM.taskList.collectAsState() // CAMBIO

        LazyColumn {
            items(dataList) { item ->
                // Esto cumple "Manipular tablas" (Eliminar)
                val delete = SwipeAction(
                    icon = rememberVectorPainter(image = Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = { dataVM.deleteTask(item) } // CAMBIO
                )

                SwipeableActionsBox(
                    startActions = listOf(delete),
                    swipeThreshold = 150.dp
                ) {
                    // Esto cumple "Despliegue" (muestra 3 tipos de datos)
                    TaskCard(
                        title = item.title,
                        priority = item.priority,
                        isCompleted = item.isCompleted
                    ) {
                        // Esto cumple "Consulta del registro"
                        navController.navigate("EditView/${item.id}")
                    }
                }
            }
        }
    }
}