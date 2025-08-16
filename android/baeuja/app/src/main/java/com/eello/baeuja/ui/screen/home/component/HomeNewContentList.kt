package com.eello.baeuja.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.ui.screen.home.HomeNewContentUiModel
import com.eello.baeuja.ui.theme.BaujaTheme


@Composable
fun HomeNewContentList(items: List<HomeNewContentUiModel> = emptyList()) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "New!! (${items.size})",
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items = items) { item ->
                HomeNewContentCard(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeNewContentList() {
    BaujaTheme {
        HomeNewContentList()
    }
}