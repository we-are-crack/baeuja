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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.R
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun HomeNewContentCard() {

    Column(
        modifier = Modifier
            .width(170.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.blackpink),
            contentDescription = "content thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),

            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "BTS - Dynamite",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            color = Color(0xFF444444),
//            lineHeight = 34.sp,
            modifier = Modifier
                .padding(top = 16.dp)
        )

//        Text(
//            text = "You can learn",
//            fontFamily = RobotoFamily,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            color = Color(0xFF444444),
////            lineHeight = 34.sp,
//            modifier = Modifier
//                .padding(top = 8.dp)
//        )

        Text(
            text = "10 Units | 123 Words",
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
    BaujaTheme {
        HomeNewContentCard()
    }
}