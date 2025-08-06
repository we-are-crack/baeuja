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
    isPreview: Boolean = false
) {
    when (layoutType) {
        LayoutType.Pager -> LearningItemPagerContainer(
            items = items,
            isPreview = isPreview,
            modifier = modifier
        )

        LayoutType.LazyRow -> LearningItemLazyRowContainer(
            items = items,
            isPreview = isPreview,
            modifier = modifier
        )
    }
}