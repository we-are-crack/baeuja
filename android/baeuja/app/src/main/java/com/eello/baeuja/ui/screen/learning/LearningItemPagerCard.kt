package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem

@Composable
fun LearningItemPagerCard(
    item: LearningItem?,
    onNavigateToDetail: (Long) -> Unit = {},
    isPreview: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
    ) {
        if (item == null) {
            Box(modifier = Modifier.fillMaxSize())
        } else {
            if (isPreview) {
                Image(
                    painter = if (item.classification == ContentClassification.POP)
                        painterResource(R.drawable.default_pop)
                    else painterResource(R.drawable.default_movie),
                    contentDescription = "content thumbnail",
                    modifier = Modifier
                        .size(84.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
            } else {
                AsyncImage(
                    model = item.thumbnailUrl,
                    contentDescription = "content thumbnail",
                    modifier = Modifier
                        .size(84.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds,
                    error = if (item.classification == ContentClassification.POP)
                        painterResource(R.drawable.default_pop)
                    else painterResource(R.drawable.default_movie)
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = item.title,
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF444444),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (item.classification) {
                            ContentClassification.POP -> item.artist
                            else -> item.director
                        } ?: "",
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF797979),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    Icon(
                        painterResource(R.drawable.detail_info_icon_16dp),
                        contentDescription = "info",
                        tint = Color(0xFF797979),
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                                onNavigateToDetail(item.id)
                            }
                    )
                }

                LearningProgressRate(item.progressRate)
            }
        }
    }
}