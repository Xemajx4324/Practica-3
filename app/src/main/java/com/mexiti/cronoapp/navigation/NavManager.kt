package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mexiti.cronoapp.ui.views.AddView
import com.mexiti.cronoapp.ui.views.EditView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.viewmodel.TaskDataViewModel
import com.mexiti.cronoapp.viewmodel.TaskEditViewModel

@Composable
fun NavManager() {
    val navController = rememberNavController()
    // Obtenemos los ViewModels aquí, Hilt se encarga de proveerlos
    val dataVM: TaskDataViewModel = hiltViewModel()
    val taskEditVM: TaskEditViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeView(navController, dataVM)
        }
        composable("AddView") {
            AddView(navController, taskEditVM, dataVM)
        }
        composable(
            "EditView/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) {
            val id = it.arguments?.getLong("id") ?: 0
            EditView(
                navController = navController,
                taskEditVM = taskEditVM,
                dataVM = dataVM,
                id = id
            )
        }
    }
}