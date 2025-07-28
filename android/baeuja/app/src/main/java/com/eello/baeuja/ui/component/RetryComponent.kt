package com.eello.baeuja.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eello.baeuja.ui.theme.BaujaTheme

@Composable
fun RetryComponent(
    message: String = "A network error occurred. Please try again.",
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRetry,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(text = "Retry", color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRetryComponent() {
    BaujaTheme {
        RetryComponent(onRetry = {})
    }
}