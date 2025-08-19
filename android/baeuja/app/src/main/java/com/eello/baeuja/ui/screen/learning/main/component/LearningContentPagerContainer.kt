package com.eello.baeuja.ui.screen.learning.main.component

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
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.ui.screen.learning.common.component.LearningContentPagerCard
import com.eello.baeuja.ui.screen.learning.main.LearningContentUiModel
import com.eello.baeuja.ui.theme.BaujaTheme

@Composable
fun LearningContentPagerContainer(
    modifier: Modifier = Modifier,
    contents: List<LearningContentUiModel>,
    onNavigateToDetail: (Long) -> Unit = {},
    onNavigateToUnitOverview: (Long) -> Unit = {},
    isPreview: Boolean = false
) {
    val pagerState = rememberPagerState { (contents.size / 2) + (contents.size % 2) }

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
            LearningContentPagerCard(
                content = contents[index],
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToUnitOverview = onNavigateToUnitOverview,
                isPreview = isPreview
            )
            if (index + 1 < contents.size) {
                LearningContentPagerCard(
                    content = contents[index + 1],
                    onNavigateToDetail = onNavigateToDetail,
                    onNavigateToUnitOverview = onNavigateToUnitOverview,
                    isPreview = isPreview
                )
            } else LearningContentPagerCard(
                content = null,
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToUnitOverview = onNavigateToUnitOverview,
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
fun PreviewLearningContentPagerContainer() {
    val tempLearningItems = listOf(
        LearningContentUiModel(
            id = 1,
            classification = Classification.POP,
            title = "Ice Cream",
            artist = "BLACKPINK",
            progressRate = 11,
            thumbnailUrl = ""
        ),
        LearningContentUiModel(
            id = 1,
            classification = Classification.POP,
            title = "Believer",
            artist = "Imagine Dragons",
            progressRate = 85,
            thumbnailUrl = ""
        ),
        LearningContentUiModel(
            id = 1,
            classification = Classification.POP,
            title = "Dynamite",
            artist = "BTS",
            progressRate = 77,
            thumbnailUrl = ""
        ),
        LearningContentUiModel(
            id = 1,
            classification = Classification.POP,
            title = "HOME SWEET HOME(feat. TAEYANG, DAESUNG)",
            artist = "G-DRAGON",
            progressRate = 54,
            thumbnailUrl = ""
        ),
        LearningContentUiModel(
            id = 1,
            classification = Classification.POP,
            title = "Drowning",
            artist = "WOODZ",
            progressRate = 100,
            thumbnailUrl = ""
        ),
    )

    BaujaTheme {
        LearningContentPagerContainer(contents = tempLearningItems, isPreview = true)
    }
}