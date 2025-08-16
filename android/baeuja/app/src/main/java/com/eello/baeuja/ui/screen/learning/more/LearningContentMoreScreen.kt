package com.eello.baeuja.ui.screen.learning.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.ui.component.BACK_BUTTON_START_MARGIN
import com.eello.baeuja.ui.component.BACK_BUTTON_TOP_MARGIN
import com.eello.baeuja.ui.component.BackButton
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.screen.learning.common.component.LearningContentPagerCard
import com.eello.baeuja.ui.screen.learning.main.LearningContentUiModel
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily

private val tempLearningItems = listOf(
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

@Composable
fun LearningContentMoreScreen(
    navController: NavController? = null,
    classification: Classification
) {
    val contentMoreViewModel: LearningContentMoreViewModel = hiltViewModel()

    LearningContentMoreRoute(
        navController = navController,
        classification = classification,
        contentMoreViewModel = contentMoreViewModel
    )
}

@Composable
fun LearningContentMoreRoute(
    navController: NavController? = null,
    classification: Classification,
    contentMoreViewModel: LearningContentMoreViewModel
) {
    val items by contentMoreViewModel.items.collectAsState()
    val isLoading by contentMoreViewModel.isLoading.collectAsState()
    val onEndOfContents: () -> Unit = {
        contentMoreViewModel.loadMoreItems(classification)
    }

    LearningContentMoreUi(
        navController = navController,
        classification = classification,
        items = items,
        isLoading = isLoading,
        onEndOfContents = onEndOfContents
    )
}

@Composable
fun LearningContentMoreUi(
    navController: NavController? = null,
    classification: Classification,
    items: List<LearningContentUiModel>,
    isLoading: Boolean = false,
    onEndOfContents: () -> Unit = {},
    isPreview: Boolean = false
) {
    val listState = rememberLazyListState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItemIndex >= totalItems
        }
    }

    LaunchedEffect(shouldLoadMore.value, isLoading) {
        if (shouldLoadMore.value && !isLoading) {
            onEndOfContents()
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backButtonRef,
            classificationTextRef,
            itemListRef) = createRefs()

        BackButton(
            navController = navController,
            modifier = Modifier.constrainAs(backButtonRef) {
                top.linkTo(parent.top, BACK_BUTTON_TOP_MARGIN)
                end.linkTo(parent.start, BACK_BUTTON_START_MARGIN)
            }
        )

        Text(
            text = "K-${classification.name}",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            letterSpacing = 2.sp,
            modifier = Modifier.constrainAs(classificationTextRef) {
                top.linkTo(parent.top, BACK_BUTTON_TOP_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .padding(top = 16.dp, bottom = 92.dp)
                .constrainAs(itemListRef) {
                    top.linkTo(backButtonRef.bottom, 8.dp)
                },
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items) { item ->
                LearningContentPagerCard(
                    content = item,
                    onNavigateToDetail = { itemId ->
                        navController?.navigate(Screen.LearningContentDetail.createRoute(-1))
                    },
                    isPreview = isPreview
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMoreItems() {
    BaujaTheme {
        LearningContentMoreUi(
            classification = Classification.DRAMA,
            items = tempLearningItems,
            isPreview = true
        )
    }
}