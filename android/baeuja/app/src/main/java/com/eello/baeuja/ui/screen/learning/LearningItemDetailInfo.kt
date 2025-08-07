package com.eello.baeuja.ui.screen.learning

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.eello.baeuja.R
import com.eello.baeuja.ui.component.OneButtonBottomBar
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily
import timber.log.Timber

@Composable
fun LearningItemInfo(itemId: Int, isPreview: Boolean = false) {
    val context = LocalContext.current
    val youtubeUrl = "https://youtu.be/WMweEpGlu_U?si=wc1JStDMN3j5YCnw"

    Timber.d("clicked item id: $itemId")

    Scaffold(
        bottomBar = {
            OneButtonBottomBar(
                buttonText = "Watch on YouTube",
                enabled = true,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, youtubeUrl.toUri())
                    context.startActivity(intent)
                }
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
                backIconRef,
                thumbnailRef,
                titleRef,
                descriptionBoxRef
            ) = createRefs()

            val textStartGuideLine = createGuidelineFromStart(30.dp)
            val startGuideline = createGuidelineFromStart(22.dp)
            val endGuideline = createGuidelineFromEnd(22.dp)

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "go back",
                tint = Color(0xFF444444),
                modifier = Modifier
                    .constrainAs(backIconRef) {
                        top.linkTo(parent.top, 54.dp)
                        end.linkTo(textStartGuideLine)
                    }
                    .clickable { Timber.d("뒤로가기 클릭") }
            )

            Box(modifier = Modifier.constrainAs(thumbnailRef) {
                top.linkTo(backIconRef.bottom, 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            ) {
                if (isPreview) {
                    Image(
                        painter = painterResource(R.drawable.default_movie),
                        contentDescription = "content thumbnail",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillBounds,
                    )
                } else {
                    AsyncImage(
                        model = "",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        contentDescription = "content thumbnail",
                        contentScale = ContentScale.FillBounds,
                        error = painterResource(R.drawable.default_movie)
                    )
                }
            }

            Text(
                text = "BTS - Butter",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(thumbnailRef.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Box(modifier = Modifier
                .constrainAs(descriptionBoxRef) {
                    top.linkTo(titleRef.bottom, 20.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                }
                .width(360.dp)
                .height(340.dp)
                .background(color = Color.White)
                .border(width = 1.dp, color = Color(0xFFeeeeee), shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "This is the title song of the 5th mini album \n" +
                            "'LOVE YOURSELF 承 'Her'' of BTS'  \n" +
                            "'LOVE YOURSELF 起承轉結 Series' mini album \n" +
                            "released on September 18, 2017. The whistle \n" +
                            "sound of the intro and the sound of the guitar \n" +
                            "garnered high expectations. \n" +
                            "If we try to interpret the lyrics, \n" +
                            "it can be interpreted as meaning that \n" +
                            "\"the relationship is already foreseen through \n" +
                            "DNA\",  that is, not by chance but by necessity.",
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
    BaujaTheme {
        LearningItemInfo(1, isPreview = true)
    }
}