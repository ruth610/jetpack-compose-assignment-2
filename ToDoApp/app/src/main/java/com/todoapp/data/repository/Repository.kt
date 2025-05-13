package com.todoapp.data.repository

import com.todoapp.data.Remote.TodoApiService
import com.todoapp.data.local.TodoDao
import com.todoapp.data.local.TodoEntity

class TodoRepository(
    private val api: TodoApiService,  //it talks to  the network (API)
    private val dao: TodoDao   // it talks to the local database (Room)
) {
    suspend fun getTodos(): List<TodoEntity> {
        return try {
            val response = api.getTodos()  // fetch from API
            val entities = response.map { TodoEntity(it.id, it.userId, it.title, it.completed) }
            dao.insertTodos(entities)  // store the in the room database if the response if ok
            entities
        } catch (e: Exception) {
            dao.getTodos() // fallback to the room database if their ann error
        }
    }

    suspend fun getTodoById(id: Int): TodoEntity? {
        return dao.getTodos().find { it.id == id }
    }
}
