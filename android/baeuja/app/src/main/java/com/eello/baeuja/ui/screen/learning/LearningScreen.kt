package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eello.baeuja.ui.component.ItemSeparator
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem

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
    val onNavigateToDetail: (Int) -> Unit = { itemId ->
        navController.navigate(Screen.LearningItemDetailInfo.createRoute(itemId))
    }

    LearningRoute(onNavigateToDetail = onNavigateToDetail)
}

@Composable
fun LearningRoute(onNavigateToDetail: (Int) -> Unit = {}) {
    LearningContent(
        learningItems = tempLearningItems,
        onNavigateToDetail = onNavigateToDetail
    )
}

@Composable
fun LearningContent(
    learningItems: Map<ContentClassification, List<LearningItem>>,
    onNavigateToDetail: (Int) -> Unit = {},
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
            isPreview = isPreview
        )

        ItemSeparator(height = 2.dp)

        LearningCategorySection(
            itemContainerLayoutType = LayoutType.LazyRow,
            classification = ContentClassification.MOVIE,
            learningItems = learningItems[ContentClassification.MOVIE] ?: emptyList(),
            onNavigateToDetail = onNavigateToDetail,
            isPreview = isPreview
        )

        ItemSeparator(height = 2.dp)

        LearningCategorySection(
            itemContainerLayoutType = LayoutType.LazyRow,
            classification = ContentClassification.DRAMA,
            learningItems = learningItems[ContentClassification.DRAMA] ?: emptyList(),
            onNavigateToDetail = onNavigateToDetail,
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