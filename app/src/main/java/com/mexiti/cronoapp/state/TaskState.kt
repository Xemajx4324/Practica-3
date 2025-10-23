package com.mexiti.cronoapp.state

// Estado para la UI de AddView y EditView
data class TaskState(
    val title: String = "",
    val priority: Int = 1,
    val isCompleted: Boolean = false,
    val dueDate: Long = System.currentTimeMillis()
)




