package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eello.baeuja.ui.component.ItemSeparator
import com.eello.baeuja.ui.component.LoadingComponent
import com.eello.baeuja.ui.component.RetryComponent
import com.eello.baeuja.ui.navigation.NavGraph
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem
import com.eello.baeuja.viewmodel.LearningMainViewModel

val LEARNING_PADDING_HORIZONTAL = 16.dp

private val tempLearningItems = mapOf(
    ContentClassification.POP to listOf(
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
    ),
    ContentClassification.MOVIE to listOf(
        LearningItem(
            classification = ContentClassification.MOVIE,
            title = "Parasite",
            progressRate = 11,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.MOVIE,
            title = "The Batman",
            progressRate = 85,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.MOVIE,
            title = "The Shawshank Redemption",
            progressRate = 77,
            thumbnailUrl = ""
        )
    ),
    ContentClassification.DRAMA to listOf(
        LearningItem(
            classification = ContentClassification.DRAMA,
            title = "Kingdom",
            progressRate = 11,
            thumbnailUrl = ""
        ),
        LearningItem(
            classification = ContentClassification.DRAMA,
            title = "Itaewon Class",
            progressRate = 85,
            thumbnailUrl = ""
        )
    )
)

@Composable
fun LearningScreen(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val learnEntry = remember(currentBackStackEntry.value) {
        navController.getBackStackEntry(NavGraph.Learn.route)
    }

    val learningViewModel: LearningMainViewModel = hiltViewModel(learnEntry)

    LearningRoute(
        navController = navController,
        learningViewModel = learningViewModel,
    )
}

@Composable
fun LearningRoute(
    navController: NavController,
    learningViewModel: LearningMainViewModel,
) {
    val isLoading by learningViewModel.isLoading.collectAsState()
    val isFailure by learningViewModel.isFailure.collectAsState()
    val items by learningViewModel.items.collectAsState()

    val onNavigateToDetail: (Long) -> Unit = { itemId ->
        navController.navigate(Screen.LearningItemDetailInfo.createRoute(itemId))
    }

    val onMoreClick: (ContentClassification) -> Unit = { classification ->
        navController.navigate(Screen.LearningItemMore.createRoute(classification))
    }

    if (isLoading) {
        LoadingComponent()
    } else {
        if (isFailure) {
            RetryComponent(onRetry = learningViewModel::loadLearningMainContents)
        } else {
            LearningContent(
                learningItems = items,
                onNavigateToDetail = onNavigateToDetail,
                onMoreClick = onMoreClick,
            )
        }
    }
}

@Composable
fun LearningContent(
    learningItems: Map<ContentClassification, List<LearningItem>>,
    onNavigateToDetail: (Long) -> Unit = {},
    onMoreClick: (ContentClassification) -> Unit = {},
    isPreview: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Learning",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.W500,
            fontSize = 22.sp,
            color = Color(0xFF444444),
            modifier = Modifier.padding(start = LEARNING_PADDING_HORIZONTAL, bottom = 16.dp)
        )

        ItemSeparator(height = 2.dp)

        LearningCategorySection(
            itemContainerLayoutType = LayoutType.Pager,
            classification = ContentClassification.POP,
            learningItems = learningItems[ContentClassification.POP] ?: emptyList(),
            onNavigateToDetail = onNavigateToDetail,
            onMoreClick = onMoreClick,
            isPreview = isPreview
        )

        ItemSeparator(height = 2.dp)

        LearningCategorySection(
            itemContainerLayoutType = LayoutType.LazyRow,
            classification = ContentClassification.MOVIE,
            learningItems = learningItems[ContentClassification.MOVIE] ?: emptyList(),
            onNavigateToDetail = onNavigateToDetail,
            onMoreClick = onMoreClick,
            isPreview = isPreview
        )

        ItemSeparator(height = 2.dp)

        LearningCategorySection(
            itemContainerLayoutType = LayoutType.LazyRow,
            classification = ContentClassification.DRAMA,
            learningItems = learningItems[ContentClassification.DRAMA] ?: emptyList(),
            onNavigateToDetail = onNavigateToDetail,
            onMoreClick = onMoreClick,
            isPreview = isPreview
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLearningContent() {
    BaujaTheme {
        LearningContent(learningItems = tempLearningItems, isPreview = true)
    }
}