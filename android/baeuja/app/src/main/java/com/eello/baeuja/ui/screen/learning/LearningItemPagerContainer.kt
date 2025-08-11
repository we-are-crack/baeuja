package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem

@Composable
fun LearningItemPagerContainer(
    modifier: Modifier = Modifier,
    items: List<LearningItem>,
    onNavigateToDetail: (Long) -> Unit = {},
    isPreview: Boolean = false
) {
    val pagerState = rememberPagerState { (items.size / 2) + (items.size % 2) }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val index = pagerState.currentPage * 2
            LearningItemPagerCard(
                item = items[index],
                onNavigateToDetail = onNavigateToDetail,
                isPreview = isPreview
            )
            if (index + 1 < items.size) {
                LearningItemPagerCard(
                    item = items[index + 1],
                    onNavigateToDetail = onNavigateToDetail,
                    isPreview = isPreview
                )
            } else LearningItemPagerCard(
                item = null,
                onNavigateToDetail = onNavigateToDetail,
                isPreview = isPreview
            )
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLearningItemPagerContainer() {
    val tempLearningItems = listOf(
        LearningItem(
            classification = ContentClassification.POP,
            title = "Ice Cream",
            artist = "BLACKPINK",
            progressRate = 11,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.POP,
            title = "Believer",
            artist = "Imagine Dragons",
            progressRate = 85,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.POP,
            title = "Dynamite",
            artist = "BTS",
            progressRate = 77,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.POP,
            title = "HOME SWEET HOME(feat. TAEYANG, DAESUNG)",
            artist = "G-DRAGON",
            progressRate = 54,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.POP,
            title = "Drowning",
            artist = "WOODZ",
            progressRate = 100,
            thumbnailUrl = ""
        ),
    )

    BaujaTheme {
        LearningItemPagerContainer(items = tempLearningItems, isPreview = true)
    }
}