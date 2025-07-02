package com.eello.bauja.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.eello.bauja.R
import com.eello.bauja.ui.theme.BaujaTheme
import kotlinx.coroutines.delay

private val guidelineStartRatio = 0.396f
private val guidelineEndRatio = 0.398f
private val logoTopMargin = 345.dp
private val appNameTopMargin = 378.7.dp
private val bottomMargin = 60.dp

@Composable
fun SplashScreen(navController: NavController) {
    // 2초 대기 후 home으로 이동
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // 화면 UI
    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    ConstraintLayout {
        val startGuideline = createGuidelineFromStart(guidelineStartRatio)
        val endGuideline = createGuidelineFromEnd(guidelineEndRatio)
        val (logo, appName) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.default_logo),
            contentDescription = stringResource(R.string.app_logo_description),
            modifier = Modifier
                .constrainAs(logo) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(parent.top, logoTopMargin)
                }
                .size(85.4.dp, 78.3.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.constrainAs(appName) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(logo.bottom, appNameTopMargin)
                bottom.linkTo(parent.bottom, bottomMargin)
            },
            fontSize = 28.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.main),
            lineHeight = 32.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    BaujaTheme {
        SplashScreenContent()
    }
}