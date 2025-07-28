package com.eello.baeuja.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eello.baeuja.R
import com.eello.baeuja.ui.theme.NotoSansKrFamily
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.LearningContentItem

@Composable
fun HomeLearningContentCard(item: LearningContentItem, isPreview: Boolean = false) {

    Column(
        modifier = Modifier
            .width(350.dp)
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.mogadishu),
                contentDescription = "content thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        } else {
            AsyncImage(
                model = item.unitThumbnailUrl,
                contentDescription = "content thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds,
                error = painterResource(R.drawable.mogadishu)
            )
        }

//        Text( // sentence
//            text = item.koreanSentence,
//            fontFamily = NotoSansKrFamily,
//            fontWeight = FontWeight.W500,
//            fontSize = 16.sp,
//            color = Color(0xFF444444),
//            modifier = Modifier
//                .padding(top = 16.dp),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
        MaskedWordText(item.koreanSentence, item.koreanWordInSentence)

        Text( // means
            text = item.englishSentence,
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color(0xFF797979),
            modifier = Modifier
                .padding(top = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Let's learn →",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                color = Color(0xFF9388e8),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(top = 8.dp)
                    .clickable { }
            )
        }
    }
}

@Composable
fun MaskedWordText(
    fullText: String,
    targetWord: String
) {
    val parts = fullText.split(targetWord)

    Row(modifier = Modifier.padding(top = 16.dp)) {
        for ((index, part) in parts.withIndex()) {
            Text(
                text = part,
                fontFamily = NotoSansKrFamily,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                color = Color(0xFF444444),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (index < parts.size - 1) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFeeeeee))
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text = "   ".repeat(targetWord.length),
                        fontSize = 16.sp,
                        color = Color.Transparent
                    )
                }
            }
        }
    }
}