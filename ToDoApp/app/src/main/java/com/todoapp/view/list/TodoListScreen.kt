package com.todoapp.view.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todoapp.data.local.TodoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todos: List<TodoEntity>,
    isLoading: Boolean,
    error: String?,
    onItemClick: (TodoEntity) -> Unit,
    onUserClick: (Int) -> Unit,
    expandedUserId: Int? // Pass expandedUserId here
) {
    // Group the todos by userId
    val groupedTodos = groupTodosByUser(todos)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My TODO List", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error != null -> {
                    Text(text = error, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 16.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                    ) {
                        // Iterate over each user
                        groupedTodos.forEach { (userId, userTodos) ->

                            item {
                                UserCard(userId = userId, onClick = { onUserClick(userId) }, expandedUserId = expandedUserId)
                            }

                            // Animate the visibility of the todo items based on expandedUserId
                            item {
                                AnimatedVisibility(
                                    visible = expandedUserId == userId,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    // Wrap the todo items inside a Column to apply animation
                                    Column(modifier = Modifier.animateContentSize()) {
                                        userTodos.forEach { todo ->
                                            TodoCard(todo = todo, onClick = { onItemClick(todo) })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(userId: Int, onClick: () -> Unit, expandedUserId: Int?) {
    // User card that will be clickable to expand/collapse the todo list
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("User ID: $userId", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f)) // Push the arrow to the right
            Icon(
                imageVector = if (expandedUserId == userId) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun TodoCard(todo: TodoEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .background(Color.Blue) // Set background color here
            .height(80.dp), // Set height here
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (todo.completed) Icons.Default.CheckCircle else Icons.Default.Circle,
                contentDescription = null,
                tint = if (todo.completed) Color.Green else Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(todo.title, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.weight(1f)) // Push the right arrow to the right
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "Right Arrow",
                tint = Color(0xFF3486C9)
            )
        }
    }
}

fun groupTodosByUser(todos: List<TodoEntity>): Map<Int, List<TodoEntity>> {
    return todos.groupBy { it.userId }
}



