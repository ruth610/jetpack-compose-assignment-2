package com.todoapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.data.local.TodoEntity
import com.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    var todos by mutableStateOf<List<TodoEntity>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            isLoading = true
            try {
                todos = repository.getTodos()
            } catch (e: Exception) {
                error = "Something went wrong!"
            } finally {
                isLoading = false
            }
        }
    }
    fun groupTodosByUser(todos: List<TodoEntity>): Map<Int, List<TodoEntity>> {
        return todos.groupBy { it.userId }
    }

}
