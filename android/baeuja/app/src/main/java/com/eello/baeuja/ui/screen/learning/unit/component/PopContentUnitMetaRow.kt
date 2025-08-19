package com.eello.baeuja.ui.screen.learning.unit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eello.baeuja.R
import com.eello.baeuja.ui.screen.learning.unit.ContentUnitUiModel
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun PopContentUnitMetaRow(
    modifier: Modifier = Modifier,
    sequence: Int = 1,
    contentUnit: ContentUnitUiModel.PopUnitUiModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            // Main Text
            text = "Unit. $sequence",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            color = Color.White,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.nav_learning_unselected_gi),
                contentDescription = "unit meta data",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${contentUnit.wordsCount} words, ${contentUnit.sentencesCount} sentences",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    progress = { contentUnit.progressRate },
                    color = Color.White, // 진행 색상
                    strokeWidth = 2.5.dp,
                    trackColor = Color.Gray,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${(contentUnit.progressRate * 100).toInt()}%",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                contentUnit.lastLearnedDate?.let {
                    Icon(
                        painter = painterResource(id = R.drawable.flag_16dp),
                        contentDescription = "last learned date",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = contentUnit.lastLearnedDate ?: "Not study yet",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
        }
    }
}