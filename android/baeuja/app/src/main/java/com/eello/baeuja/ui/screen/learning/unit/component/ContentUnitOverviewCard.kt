package com.eello.baeuja.ui.screen.learning.unit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eello.baeuja.R
import com.eello.baeuja.domain.content.model.SentenceType
import com.eello.baeuja.ui.screen.learning.unit.ContentUnitUiModel

@Composable
fun ContentUnitOverviewCard(
    modifier: Modifier = Modifier,
    sequence: Int = 1,
    contentUnit: ContentUnitUiModel,
    onClickBookmarkButton: (Int) -> Unit = {},
    isPreview: Boolean = false
) {

    Box(
        modifier = modifier
            .size(370.dp, 160.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        // 배경 이미지

        if (isPreview) {
            Image(
                painter = painterResource(
                    id = when (contentUnit) {
                        is ContentUnitUiModel.PopUnitUiModel -> {
                            R.drawable.default_pop
                        }

                        else -> {
                            R.drawable.default_movie
                        }
                    }
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = contentUnit.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(
                    id = when (contentUnit) {
                        is ContentUnitUiModel.PopUnitUiModel -> {
                            R.drawable.default_pop
                        }

                        else -> {
                            R.drawable.default_movie
                        }
                    }
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x1C000000), // 위쪽 (거의 투명)
                            Color(0xAB000000)  // 아래쪽 (짙은 검정)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        if (contentUnit is ContentUnitUiModel.VisualUnitUiModel) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
                    .clickable { onClickBookmarkButton(sequence - 1) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.unit_bookmarked_16dp),
                    contentDescription = "bookmark",
                    tint = if (contentUnit.isBookmarked) Color(0xFFffce0f) else Color(0xFFcccccc),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        ContentUnitMetaRow(sequence = sequence, contentUnit = contentUnit)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContentUnitOverviewCard() {
    val popContentUnit = ContentUnitUiModel.PopUnitUiModel(
        unitId = 1,
        thumbnailUrl = "",
        lastLearnedDate = "2020.02.20",
        sentencesCount = 10,
        wordsCount = 100,
        progressRate = 0.5f
    )

    val visualContentUnit = ContentUnitUiModel.VisualUnitUiModel(
        unitId = 1,
        thumbnailUrl = "",
        lastLearnedDate = "2020.02.20",
        korean = "한국어",
        english = "English",
        isBookmarked = true,
        sentenceType = SentenceType.FAMOUS_LINE
    )

    ContentUnitOverviewCard(
//        contentUnit = popContentUnit,
        contentUnit = visualContentUnit,
        isPreview = true
    )
}