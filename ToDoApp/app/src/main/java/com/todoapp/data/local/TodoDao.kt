package com.todoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    suspend fun getTodos(): List<TodoEntity>
//    If a todo with the same id exists, it will replace it
    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    this can run asynchronously without blocking the UI( suspend fun)
//    Adds (or updates) a list of todos into the Room DB
    suspend fun insertTodos(todos: List<TodoEntity>)
}
