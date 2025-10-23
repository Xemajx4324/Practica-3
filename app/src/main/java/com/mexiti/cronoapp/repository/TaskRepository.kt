package com.mexiti.cronoapp.repository

import com.mexiti.cronoapp.model.Task
import com.mexiti.cronoapp.room.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getTasks()
    fun getTaskById(id: Long): Flow<Task> = taskDao.getTaskById(id)
    suspend fun insertTask(task: Task) = taskDao.insert(task)
    suspend fun updateTask(task: Task) = taskDao.update(task)
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
}
