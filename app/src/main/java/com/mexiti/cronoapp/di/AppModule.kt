package com.mexiti.cronoapp.di

import android.content.Context
import androidx.room.Room
import com.mexiti.cronoapp.room.TaskDatabase
import com.mexiti.cronoapp.room.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun providesTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TaskDatabase::class.java,
            name = "task_db" // Nuevo nombre de la base de datos
        ).fallbackToDestructiveMigration()
            .build()
    }
}


