package com.eello.baeuja.ui.screen.learning.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.R
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun LearningProgressRate(progressRate: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "progress rate : ",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = colorResource(R.color.content_text_main_color),
        )

        Spacer(modifier = Modifier.width(4.dp))

        LearningProgressDots(progressRate)
    }
}

@Composable
fun LearningProgressDots(
    progress: Int, // 진행도 0 ~ 5
    total: Int = 5,
    modifier: Modifier = Modifier,
    activeColor: Color = colorResource(R.color.content_text_main_color),
    inactiveColor: Color = Color(0xFFe0dcff),
    dotSize: Dp = 6.dp,
    spacing: Dp = 4.dp
) {
    val progressStage = progress / 20

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacing)) {
        repeat(total) { index ->
            Canvas(modifier = Modifier.size(dotSize)) {
                drawCircle(
                    color = if (index < progressStage) activeColor else inactiveColor
                )
            }
        }
    }
}