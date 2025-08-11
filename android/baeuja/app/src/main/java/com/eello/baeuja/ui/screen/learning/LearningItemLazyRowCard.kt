package com.eello.baeuja.ui.screen.learning

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eello.baeuja.R
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem

private val IMAGE_WIDTH = 170.dp
private val IMAGE_HEIGHT = 200.dp

@Composable
fun LearningItemLazyRowCard(
    item: LearningItem,
    onNavigateToDetail: (Long) -> Unit = {},
    isPreview: Boolean = false
) {
    Column(
        modifier = Modifier.width(IMAGE_WIDTH),
    ) {

        if (isPreview) {
            Image(
                painter = if (item.classification == ContentClassification.POP)
                    painterResource(R.drawable.default_pop)
                else painterResource(R.drawable.default_movie),
                contentDescription = "content thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
            )
        } else {
            AsyncImage(
                model = item.thumbnailUrl,
                contentDescription = "content thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
                error = if (item.classification == ContentClassification.POP)
                    painterResource(R.drawable.default_pop)
                else painterResource(R.drawable.default_movie)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.title,
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF444444),
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
                    .clickable { onNavigateToDetail(item.id) }
            )
        }

        LearningProgressRate(item.progressRate)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLearningItemLazyRowCard() {
    val learningItem = LearningItem(
        classification = ContentClassification.MOVIE,
        title = "Parasite",
        progressRate = 11,
        thumbnailUrl = ""
    )

    BaujaTheme {
        LearningItemLazyRowCard(item = learningItem, isPreview = true)
    }
}