package com.mexiti.cronoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.repository.TaskRepository
import com.mexiti.cronoapp.state.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(TaskState())
        private set

    // Función para la consulta de registro
    fun getTaskById(id: Long) {
        viewModelScope.launch {
            repository.getTaskById(id).collectLatest { item ->
                state = state.copy(
                    title = item.title,
                    priority = item.priority,
                    isCompleted = item.isCompleted,
                    dueDate = item.dueDate
                )
            }
        }
    }

    // Funciones para actualizar el estado desde la UI
    fun onTitleChange(title: String) {
        state = state.copy(title = title)
    }

    fun onPriorityChange(priority: Int) {
        state = state.copy(priority = priority)
    }

    fun onCompletedChange(completed: Boolean) {
        state = state.copy(isCompleted = completed)
    }

    fun onDateChange(date: Long) {
        state = state.copy(dueDate = date)
    }

    // Limpia el estado (similar al 'detener' del cronómetro)
    fun resetState() {
        state = TaskState()
    }
}