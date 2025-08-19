package com.eello.baeuja.ui.screen.learning.main.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eello.baeuja.ui.screen.learning.main.LearningContentUiModel

enum class LayoutType { Pager, LazyRow }

@Composable
fun LearningContentContainer(
    layoutType: LayoutType,
    modifier: Modifier = Modifier,
    items: List<LearningContentUiModel>,
    onNavigateToDetail: (Long) -> Unit = {},
    onNavigateToUnitOverview: (Long) -> Unit = {},
    isPreview: Boolean = false
) {
    when (layoutType) {
        LayoutType.Pager -> LearningContentPagerContainer(
            modifier = modifier,
            contents = items,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToUnitOverview = onNavigateToUnitOverview,
            isPreview = isPreview
        )

        LayoutType.LazyRow -> LearningContentLazyRowContainer(
            modifier = modifier,
            contents = items,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToUnitOverview = onNavigateToUnitOverview,
            isPreview = isPreview
        )
    }
}