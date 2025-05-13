package com.todoapp.view.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todoapp.data.local.TodoEntity
import com.todoapp.viewModel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(todo: TodoEntity?, onBack: () -> Unit) {
    if (todo == null) {
        Text("Todo not found", style = MaterialTheme.typography.bodyLarge)
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details", style = MaterialTheme.typography.titleLarge, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(color = Color(0xFFF2F2F2)) // Light gray background
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            SectionCard(title = "Title", content = "${todo.title}",
                modifier = Modifier
                    .padding(top = 16.dp) // Spacing between sections
            )
            // User ID Section
            SectionCard(title = "User ID", content = "${todo.userId}",
                modifier = Modifier.padding(top = 16.dp) // Spacing between sections
                )

            // ID Section
            SectionCard(title = "ID", content = "${todo.id}",
                modifier = Modifier.padding(top = 16.dp) // Spacing between sections
                )

            // Status Section
            SectionCard(
                title = "Status",
                content = if (todo.completed) "Completed ✅" else "Pending ⏳",
                modifier = Modifier.padding(top = 16.dp) // Spacing between sections
            )
        }
    }
}


@Composable
fun SectionCard(title: String, content: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFE0E0E0)) // Background color for each row
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            content,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}



