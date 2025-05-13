package com.todoapp.navigation

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Detail : Screen("detail/{todoId}") {
        fun passId(id: Int) = "detail/$id"
    }
}
