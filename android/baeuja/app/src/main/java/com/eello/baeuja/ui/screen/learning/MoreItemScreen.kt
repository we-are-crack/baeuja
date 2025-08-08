package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.eello.baeuja.ui.component.BACK_BUTTON_START_MARGIN
import com.eello.baeuja.ui.component.BACK_BUTTON_TOP_MARGIN
import com.eello.baeuja.ui.component.BackButton
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem

private val tempLearningItems = listOf(
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

@Composable
fun MoreItemScreen(
    navController: NavController? = null,
    classification: ContentClassification
) {
    val items = tempLearningItems

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (backButtonRef,
            itemListRef) = createRefs()

        BackButton(
            navController = navController,
            modifier = Modifier.constrainAs(backButtonRef) {
                top.linkTo(parent.top, BACK_BUTTON_TOP_MARGIN)
                end.linkTo(parent.start, BACK_BUTTON_START_MARGIN)
            }
        )

        LazyColumn(
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
                LearningItemPagerCard(
                    item = item,
                    onNavigateToDetail = { itemId ->
                        navController?.navigate(Screen.LearningItemDetailInfo.createRoute(-1))
                    },
                    isPreview = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMoreItems() {
    BaujaTheme {
        MoreItemScreen(classification = ContentClassification.POP)
    }
}