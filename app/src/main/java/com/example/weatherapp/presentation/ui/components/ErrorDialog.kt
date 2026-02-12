package com.example.weatherapp.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    message: String,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Error") },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    )
}