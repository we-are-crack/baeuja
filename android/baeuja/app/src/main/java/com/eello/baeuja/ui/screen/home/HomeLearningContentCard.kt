package com.eello.baeuja.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.R
import com.eello.baeuja.ui.theme.NotoSansKrFamily
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun HomeLearningContentCard() {

    Column(
        modifier = Modifier
            .width(350.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.mogadishu),
            contentDescription = "content thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Text( // sentence
            text = "첫 눈에 널 알아보게 됐어",
            fontFamily = NotoSansKrFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            color = Color(0xFF444444),
            modifier = Modifier
                .padding(top = 16.dp)
        )

        Text( // means
            text = "I recognized you at first sight",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color(0xFF797979),
            modifier = Modifier
                .padding(top = 8.dp)
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