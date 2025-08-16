package com.eello.baeuja.ui.screen.learning.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.ui.screen.learning.main.LEARNING_PADDING_HORIZONTAL
import com.eello.baeuja.ui.screen.learning.main.LearningContentUiModel
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun LearningClassificationSection(
    itemContainerLayoutType: LayoutType,
    classification: Classification,
    contents: List<LearningContentUiModel>,
    onNavigateToDetail: (Long) -> Unit = {},
    onMoreClick: (Classification) -> Unit = {},
    isPreview: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LEARNING_PADDING_HORIZONTAL)
            .padding(top = 16.dp, bottom = 8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = when (classification) {
                    Classification.POP -> "K-Pop"
                    Classification.MOVIE -> "K-Movie"
                    Classification.DRAMA -> "K-Drama"
                },
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
                color = Color(0xFF444444),
                textAlign = TextAlign.Left,
                modifier = Modifier.alignByBaseline()
            )

            Text(
                text = "more",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFFaaaaaa),
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .alignByBaseline()
                    .clickable { onMoreClick(classification) },
            )
        }

        LearningContentContainer(
            layoutType = itemContainerLayoutType,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            items = contents,
            onNavigateToDetail = onNavigateToDetail,
            isPreview = isPreview
        )
    }
}