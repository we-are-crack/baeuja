package com.eello.baeuja.ui.screen.learning.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eello.baeuja.ui.screen.learning.main.LearningContentUiModel

@Composable
fun LearningContentLazyRowContainer(
    modifier: Modifier = Modifier,
    contents: List<LearningContentUiModel>,
    onNavigateToDetail: (Long) -> Unit = {},
    onNavigateToUnitOverview: (Long) -> Unit = {},
    isPreview: Boolean = false
) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(contents.size) { index ->
            LearningContentLazyRowCard(
                content = contents[index],
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToUnitOverview = onNavigateToUnitOverview,
                isPreview = isPreview
            )
        }
    }
}