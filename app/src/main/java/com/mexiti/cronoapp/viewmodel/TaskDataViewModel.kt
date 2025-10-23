package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.model.Task
import com.mexiti.cronoapp.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDataViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList = _taskList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest {
                _taskList.value = it
            }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }
}