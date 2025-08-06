package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eello.baeuja.viewmodel.LearningItem

@Composable
fun LearningItemLazyRowContainer(
    modifier: Modifier = Modifier,
    items: List<LearningItem>,
    isPreview: Boolean = false
) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items.size) { index ->
            LearningItemLazyRowCard(items[index], isPreview)
        }
    }
}