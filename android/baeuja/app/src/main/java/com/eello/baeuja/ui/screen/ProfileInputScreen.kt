package com.eello.baeuja.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.eello.baeuja.R
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.NotoSansKrFamily
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun ProfileInputScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfafafa))
    ) {
        val (
            backIcon,
            welcomeText,
            guideText,
            nicknameInput,
        ) = createRefs()

        val textStartGuideLine = createGuidelineFromStart(30.dp)
        val inputFiledStartGuideLine = createGuidelineFromStart(22.dp)
        val inputFiledEndGuideLine = createGuidelineFromEnd(22.dp)

        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "go back",
            tint = Color(0xFF444444),
            modifier = Modifier.constrainAs(backIcon) {
                top.linkTo(parent.top, 54.dp)
                end.linkTo(textStartGuideLine)
            }
        )

        Text(
            text = "Welcome to Baeuja!",
            fontFamily = NotoSansKrFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = colorResource(R.color.content_text_main_color),
            lineHeight = 36.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(backIcon.bottom, 30.dp)
                start.linkTo(textStartGuideLine)
            }
        )

        Text(
            text = "Please enter your\ninformation before starting",
            fontFamily = NotoSansKrFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = Color(0xFF666666),
            lineHeight = 30.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.constrainAs(guideText) {
                top.linkTo(welcomeText.bottom, 12.dp)
                start.linkTo(textStartGuideLine)
            }
        )

        var text by remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .constrainAs(nicknameInput) {
                    top.linkTo(guideText.bottom, 50.dp)
                    start.linkTo(inputFiledStartGuideLine)
                    end.linkTo(inputFiledEndGuideLine)
                    width = Dimension.fillToConstraints
                }
                .height(72.dp)
                .border(
                    border = BorderStroke(1.dp, Color(0xFFcccccc)),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
        ) {
            val startPadding = 16.dp
            Column (verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Nickname",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    color = colorResource(R.color.content_text_main_color),
                    modifier = Modifier.padding(start = startPadding, top = 8.dp)
                        .height(16.dp)
                )

                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.padding(start = startPadding, end = startPadding)
                        .wrapContentWidth()
                        .height(24.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 26.sp,
                    ),
                    decorationBox = { innerTextField ->
                        Box {
                            if (text.isEmpty()) {
                                Text(
                                    text = "Enter your nickname..",
                                    color = Color(0xFFcccccc),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    BaujaTheme {
        ProfileInputScreen()
    }
}