package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.todoapp.data.Remote.RetrofitInstance
import com.todoapp.data.local.AppDatabase
import com.todoapp.data.repository.TodoRepository
import com.todoapp.navigation.Screen
import com.todoapp.view.detailScreen.TodoDetailScreen
import com.todoapp.view.list.TodoListScreen
import com.todoapp.viewModel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val repository = TodoRepository(RetrofitInstance.api, db.todoDao())
        val viewModel = TodoViewModel(repository)

        setContent {
            AppNavGraph(viewModel = viewModel)
        }
    }
}



@Composable
fun AppNavGraph(viewModel: TodoViewModel) {
    val navController = rememberNavController()
    var expandedUserId by remember { mutableStateOf<Int?>(null) }

    NavHost(navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            TodoListScreen(
                todos = viewModel.todos,
                isLoading = viewModel.isLoading,
                error = viewModel.error,
                onItemClick = { todo ->
                    navController.navigate(Screen.Detail.passId(todo.id))
                },
                onUserClick = { userId ->
                    // Toggle expansion of user todos
                    expandedUserId = if (expandedUserId == userId) null else userId
                },
                expandedUserId = expandedUserId // Pass expandedUserId to TodoListScreen
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            val todo = viewModel.todos.find { it.id == todoId }

            if (todo != null) {
                TodoDetailScreen(todo = todo, onBack = { navController.popBackStack() })
            }
        }
    }
}




//  UI (Jetpack Compose)
//  ↓
//  ViewModel (calls Repository)
//  ↓
//  TodoRepository
//  ↙         ↘
//  Room DB     API (Retrofit)

//        TodoApiService.kt	Defines remote API endpoints using Retrofit
//        RetrofitInstance.kt	Provides a singleton Retrofit client
//        TodoRepository.kt	Core logic: fetch from network, save to DB, fallback to DB

