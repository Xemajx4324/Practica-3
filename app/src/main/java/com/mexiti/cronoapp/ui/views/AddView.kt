package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mexiti.cronoapp.model.Task
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.ui.components.MainTextField
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.ui.components.PrioritySelector // IMPORTANTE
import com.mexiti.cronoapp.viewmodel.TaskDataViewModel
import com.mexiti.cronoapp.viewmodel.TaskEditViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
    navController: NavController,
    taskEditVM: TaskEditViewModel,
    dataVM: TaskDataViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Nueva Tarea") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        ContentAddView(it, navController, taskEditVM, dataVM)
    }
}

@Composable
fun ContentAddView(
    it: PaddingValues,
    navController: NavController,
    taskEditVM: TaskEditViewModel,
    dataVM: TaskDataViewModel
) {
    val state = taskEditVM.state
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = sdf.format(Date(state.dueDate))

    Column(
        modifier = Modifier
            .padding(it)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTextField(
            value = state.title,
            onValueChange = { taskEditVM.onTitleChange(it) },
            label = "Título de la Tarea"
        )
        Spacer(modifier = Modifier.height(16.dp)) // Más espacio

        PrioritySelector(
            currentPriority = state.priority,
            onPrioritySelected = { taskEditVM.onPriorityChange(it) }
        )


        Spacer(modifier = Modifier.height(16.dp))

        Text("Fecha: $currentDate")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            dataVM.addTask(
                Task(
                    title = state.title,
                    priority = state.priority,
                    isCompleted = false,
                    dueDate = state.dueDate
                )
            )
            navController.popBackStack()
        }) {
            Text(text = "Guardar Tarea")
        }

        DisposableEffect(Unit) {
            onDispose {
                taskEditVM.resetState()
            }
        }
    }
}