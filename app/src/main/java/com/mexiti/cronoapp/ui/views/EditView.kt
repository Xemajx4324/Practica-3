package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
fun EditView(
    navController: NavController,
    taskEditVM: TaskEditViewModel,
    dataVM: TaskDataViewModel,
    id: Long
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Editar Tarea") },
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
        ContentEditView(
            it = it,
            navController = navController,
            taskEditVM = taskEditVM,
            dataVM = dataVM,
            id = id
        )
    }
}

@Composable
fun ContentEditView(
    it: PaddingValues,
    navController: NavController,
    taskEditVM: TaskEditViewModel,
    dataVM: TaskDataViewModel,
    id: Long
) {
    val state = taskEditVM.state
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = sdf.format(Date(state.dueDate))

    LaunchedEffect(key1 = Unit) {
        taskEditVM.getTaskById(id)
    }

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
        Spacer(modifier = Modifier.height(16.dp))

        // ***** AQUÍ ESTÁ EL CAMBIO *****
        PrioritySelector(
            currentPriority = state.priority,
            onPrioritySelected = { taskEditVM.onPriorityChange(it) }
        )
        // ********************************

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Completada:")
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = state.isCompleted,
                onCheckedChange = { taskEditVM.onCompletedChange(it) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text("Fecha: $currentDate")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            dataVM.updateTask(
                Task(
                    id = id,
                    title = state.title,
                    priority = state.priority,
                    isCompleted = state.isCompleted,
                    dueDate = state.dueDate
                )
            )
            navController.popBackStack()
        }) {
            Text(text = "Guardar Cambios")
        }

        DisposableEffect(Unit) {
            onDispose {
                taskEditVM.resetState()
            }
        }
    }
}