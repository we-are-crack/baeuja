package com.eello.baeuja.ui.screen.learning

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.eello.baeuja.R
import com.eello.baeuja.ui.component.BACK_BUTTON_START_MARGIN
import com.eello.baeuja.ui.component.BACK_BUTTON_TOP_MARGIN
import com.eello.baeuja.ui.component.BackButton
import com.eello.baeuja.ui.component.LoadingComponent
import com.eello.baeuja.ui.component.OneButtonBottomBar
import com.eello.baeuja.ui.component.RetryComponent
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItemDetail
import com.eello.baeuja.viewmodel.LearningItemDetailInfoViewModel
import timber.log.Timber

@Composable
fun LearningItemInfo(
    navController: NavController? = null,
    itemId: Long,
) {
    val detailInfoViewModel: LearningItemDetailInfoViewModel = hiltViewModel()
    val context = LocalContext.current

    LearningItemInfoRoute(
        navController = navController,
        context = context,
        itemId = itemId,
        detailInfoViewModel = detailInfoViewModel
    )
}

@Composable
fun LearningItemInfoRoute(
    navController: NavController? = null,
    context: Context,
    itemId: Long,
    detailInfoViewModel: LearningItemDetailInfoViewModel,
) {
    LaunchedEffect(Unit) {
        detailInfoViewModel.loadDetailInfo(itemId)
    }

    val isLoading by detailInfoViewModel.isLoading.collectAsState()
    val isFailure by detailInfoViewModel.isFailure.collectAsState()
    val detailInfo by detailInfoViewModel.detailInfo.collectAsState()

    val watchOnYoutubeOnClick: () -> Unit = {
        val youtubeUrl = "https://www.youtube.com/watch?v=${detailInfo?.youtubeId}"
        Timber.d("click youtube button, video url: $youtubeUrl")

        val intent = Intent(Intent.ACTION_VIEW, youtubeUrl.toUri())
        context.startActivity(intent)
    }

    if (isLoading) {
        LoadingComponent()
    } else {
        if (detailInfo == null || isFailure) {
            RetryComponent { detailInfoViewModel.loadDetailInfo(itemId) }
        } else {
            LearningItemInfoContent(
                navController = navController,
                buttonOnClick = watchOnYoutubeOnClick,
                detailInfo = detailInfo!!
            )
        }
    }
}

@Composable
fun LearningItemInfoContent(
    navController: NavController? = null,
    buttonOnClick: () -> Unit = {},
    detailInfo: LearningItemDetail,
    isPreview: Boolean = false
) {
    Scaffold(
        bottomBar = {
            OneButtonBottomBar(
                buttonText = "Watch on YouTube",
                enabled = true,
                onClick = { buttonOnClick() }
            )
        }
    ) { innerPadding ->
        val paddingValues = PaddingValues(
            top = 0.dp,
            bottom = 0.dp,
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFfafafa))
        ) {
            val (
                backButtonRef,
                thumbnailRef,
                titleRef,
                descriptionBoxRef
            ) = createRefs()

            val startGuideline = createGuidelineFromStart(22.dp)
            val endGuideline = createGuidelineFromEnd(22.dp)

            BackButton(
                navController = navController,
                modifier = Modifier.constrainAs(backButtonRef) {
                    top.linkTo(parent.top, BACK_BUTTON_TOP_MARGIN)
                    end.linkTo(parent.start, BACK_BUTTON_START_MARGIN)
                }
            )

            Box(modifier = Modifier.constrainAs(thumbnailRef) {
                top.linkTo(backButtonRef.bottom, 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                if (isPreview) {
                    Image(
                        painter = if (detailInfo.classification == ContentClassification.POP)
                            painterResource(R.drawable.default_pop)
                        else painterResource(R.drawable.default_movie),
                        contentDescription = "content thumbnail",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillBounds,
                    )
                } else {
                    AsyncImage(
                        model = detailInfo.thumbnailUrl,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        contentDescription = "content thumbnail",
                        contentScale = ContentScale.FillBounds,
                        error = if (detailInfo.classification == ContentClassification.POP)
                            painterResource(R.drawable.default_pop)
                        else painterResource(R.drawable.default_movie)
                    )
                }
            }

            Text(
                text = "${
                    if (detailInfo.classification == ContentClassification.POP) detailInfo.artist
                    else detailInfo.director
                } - ${detailInfo.title}",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(thumbnailRef.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Box(
                modifier = Modifier
                    .constrainAs(descriptionBoxRef) {
                        top.linkTo(titleRef.bottom, 20.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    }
                    .width(360.dp)
                    .height(340.dp)
                    .background(color = Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFeeeeee),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = detailInfo.description,
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = Color(0xFF444444),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLearningItemInfo() {
    val tempDetailInfo = LearningItemDetail(
        id = 1,
        classification = ContentClassification.POP,
        title = "뛰어",
        artist = "BLACKPINK",
        youtubeId = "CgCVZdcKcqY",
        description = "description",
        thumbnailUrl = "https://i.namu.wiki/i/N7eH3sdCtsCBcADRDuFpaqyDW-3eCzMthYcL6b44yqyckWM_30tRh7JOM3YNB_375elgYWKkWwBVLoY6LGrngg.webp",
    )

    BaujaTheme {
        LearningItemInfoContent(
            detailInfo = tempDetailInfo,
            buttonOnClick = {},
            isPreview = true
        )
    }
}