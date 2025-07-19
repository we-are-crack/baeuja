package com.eello.baeuja.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eello.baeuja.R
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

private val guidelineStartRatio = 0.396f
private val guidelineEndRatio = 0.398f
private val logoTopMargin = 345.dp
private val appNameTopMargin = 378.7.dp
private val bottomMargin = 60.dp

@Composable
fun SplashScreen(
    navController: NavController,
) {
    val splashViewModel: SplashViewModel = hiltViewModel()

    SplashRoute(navController, splashViewModel)
}

@Composable
private fun SplashRoute(
    navController: NavController,
    splashViewModel: SplashViewModel
) {
    val isSignedIn by splashViewModel.isSignedIn.collectAsState()
    val isCheckCompleted by splashViewModel.isCheckCompleted.collectAsState()

    LaunchedEffect(Unit) {
        splashViewModel.checkSignInStatus()
    }

    LaunchedEffect(isCheckCompleted) {
        if (isCheckCompleted) {
            delay(2000L)

            if (isSignedIn) {
                Log.i("SplashRoute", "유저 인증 성공: Splash -> Home")
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            } else {
                Log.i("SplashRoute", "유저 인증 실패: Splash -> SignIn")
                navController.navigate(Screen.SignIn.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
    }

    SplashContent()
}

@Composable
private fun SplashContent() {
    ConstraintLayout {
        val startGuideline = createGuidelineFromStart(guidelineStartRatio)
        val endGuideline = createGuidelineFromEnd(guidelineEndRatio)
        val (logo, appName) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.baeuja_logo),
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
        SplashContent()
    }
}