package com.eello.baeuja.ui.screen.learning.unit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eello.baeuja.domain.content.model.SentenceType
import com.eello.baeuja.ui.component.BACK_BUTTON_TOP_MARGIN
import com.eello.baeuja.ui.component.BackButton
import com.eello.baeuja.ui.component.LoadingComponent
import com.eello.baeuja.ui.component.RetryComponent
import com.eello.baeuja.ui.screen.learning.unit.component.ContentUnitOverviewCard
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun ContentUnitOverviewScreen(
    navController: NavController,
    contentId: Long,
) {
    ContentUnitOverviewRoute(
        navController = navController,
        contentId = contentId
    )
}

@Composable
fun ContentUnitOverviewRoute(
    navController: NavController,
    contentId: Long,
    viewModel: UnitOverviewViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadContentUnits(contentId)
    }

    val uiState by viewModel.uiState.collectAsState()

    val onClickBookmarkButton: (Int) -> Unit = {
        viewModel.toggleBookmark(it)
    }

    if (uiState.isLoading) {
        LoadingComponent()
    } else if (uiState.isLoadCompleted) {
        if (uiState.isFailure) {
            RetryComponent { viewModel.loadContentUnits(contentId) }
        } else {
            ContentUnitOverviewUi(
                navController = navController,
                contentTitle = uiState.title,
                contentUnits = uiState.units,
                onClickBookmarkButton = onClickBookmarkButton
            )
        }
    }
}

@Composable
fun ContentUnitOverviewUi(
    navController: NavController? = null,
    contentTitle: String = "Content Title",
    contentUnits: List<ContentUnitUiModel> = emptyList(),
    onClickBookmarkButton: (Int) -> Unit = {},
    isPreview: Boolean = false
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (
            backButtonRef,
            unitOverviewListRef,
        ) = createRefs()

        Row(
            modifier = Modifier.constrainAs(backButtonRef) {
                top.linkTo(parent.top, BACK_BUTTON_TOP_MARGIN)
                start.linkTo(parent.start)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(
                navController = navController,
            )

            Text(
                text = contentTitle,
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(unitOverviewListRef) {
                    top.linkTo(backButtonRef.bottom, 24.dp)
                }
                .verticalScroll(rememberScrollState())
                .padding(bottom = 108.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            contentUnits.forEachIndexed { index, it ->
                ContentUnitOverviewCard(
                    sequence = index + 1,
                    contentUnit = it,
                    onClickBookmarkButton = onClickBookmarkButton,
                    isPreview = isPreview
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContentUnitOverviewScreen() {
    val previewPopContentUnits = listOf(
        ContentUnitUiModel.PopUnitUiModel(
            unitId = 1,
            thumbnailUrl = "",
            lastLearnedDate = "2020.02.20",
            sentencesCount = 10,
            wordsCount = 100,
            progressRate = 0.5f
        )
    )

    val previewVisualContentUnits = listOf(
        ContentUnitUiModel.VisualUnitUiModel(
            unitId = 1,
            thumbnailUrl = "",
            lastLearnedDate = "2020.02.20",
            korean = "한국어",
            english = "English",
            isBookmarked = true,
            sentenceType = SentenceType.FAMOUS_LINE
        )
    )

    BaujaTheme {
        ContentUnitOverviewUi(
//            contentUnits = previewPopContentUnits,
            contentUnits = previewVisualContentUnits,
            isPreview = true
        )
    }
}