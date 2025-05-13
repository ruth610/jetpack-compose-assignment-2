package com.todoapp.data.Remote

import com.todoapp.domain.model.Todo
import retrofit2.http.GET

interface TodoApiService {
//    This connects to the remote JSONPlaceholder API to fetch todos.
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}
