package com.eello.baeuja.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.eello.baeuja.viewmodel.NewContentItem

@Composable
fun HomeNewContentCard(item: NewContentItem, isPreview: Boolean = false) {

    Column(
        modifier = Modifier
            .width(170.dp)
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.blackpink),
                contentDescription = "content thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),

                contentScale = ContentScale.FillBounds
            )
        } else {
            AsyncImage(
                model = item.thumbnailUrl,
                contentDescription = "content thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds,
                error = if (item.classification == ContentClassification.POP)
                    painterResource(R.drawable.default_pop)
                else painterResource(R.drawable.default_movie)
            )
        }

        Text(
            text = item.title,
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            color = Color(0xFF444444),
            modifier = Modifier
                .padding(top = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${item.unitCount} Units | ${item.wordCount} Words",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color(0xFF9388e8),
//            lineHeight = 34.sp,
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewContentItem() {
    val item = NewContentItem(
        title = "BTS - Dynamic",
        unitCount = 10,
        wordCount = 123,
        thumbnailUrl = "",
        classification = ContentClassification.POP
    )
    BaujaTheme {
        HomeNewContentCard(item, isPreview = true)
    }
}