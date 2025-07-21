package com.eello.baeuja.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eello.baeuja.ui.theme.BaujaTheme

@Composable
fun HomeScreen() {
    HomeContent()
}

@Composable
fun HomeContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            HomeNewContentItem()
            ItemSeparator(4.dp)
        }

        items(10) { index ->
            HomeLearningContentItem()

            if (index < 9) {
                ItemSeparator(2.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BaujaTheme {
        HomeContent()
    }
}