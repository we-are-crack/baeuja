package com.eello.baeuja.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.ui.theme.NotoSansKrFamily
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun HomeLearningContentItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "#첫눈",
                fontFamily = NotoSansKrFamily,
                fontWeight = FontWeight.W500,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .alignByBaseline()
            )

            Text(
                text = "importance 3",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Right,
                color = Color(0xFF9388e8),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .alignByBaseline()
            )
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(10) {
                HomeLearningContentCard()
            }
        }
    }
}