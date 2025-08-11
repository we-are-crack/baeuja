package com.eello.baeuja.ui.screen.learning

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eello.baeuja.viewmodel.LearningItem

enum class LayoutType { Pager, LazyRow }

@Composable
fun LearningItemContainer(
    layoutType: LayoutType,
    modifier: Modifier = Modifier,
    items: List<LearningItem>,
    onNavigateToDetail: (Long) -> Unit = {},
    isPreview: Boolean = false
) {
    when (layoutType) {
        LayoutType.Pager -> LearningItemPagerContainer(
            modifier = modifier,
            items = items,
            onNavigateToDetail = onNavigateToDetail,
            isPreview = isPreview
        )

        LayoutType.LazyRow -> LearningItemLazyRowContainer(
            modifier = modifier,
            items = items,
            onNavigateToDetail = onNavigateToDetail,
            isPreview = isPreview
        )
    }
}