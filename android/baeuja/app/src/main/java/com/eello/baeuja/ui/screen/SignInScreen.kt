package com.eello.baeuja.ui.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eello.baeuja.R
import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.ui.navigation.NavGraph
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.NotoSansKrFamily
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.utils.auth.getGoogleSignInClient
import com.eello.baeuja.utils.auth.handleGoogleSignInResult
import com.eello.baeuja.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

@Composable
fun SignInScreen(navController: NavController) {
    val authEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(NavGraph.Auth.route)
    }

    val signInViewModel: SignInViewModel = hiltViewModel(authEntry)

    SignInRoute(navController, signInViewModel)
}

@Composable
fun SignInRoute(navController: NavController, signInViewModel: SignInViewModel) {
    val signInResult by signInViewModel.signInResult.collectAsState()
    val isHomeContentLoaded by signInViewModel.isHomeContentLoaded.collectAsState()
    LaunchedEffect(signInResult, isHomeContentLoaded) {
        when (signInResult) {
            AuthResult.Unregistered -> {
                navController.navigate(Screen.ProfileInput.route) {
                    popUpTo(Screen.SignIn.route) { inclusive = false }
                }
            }

            AuthResult.Success -> {
                if (isHomeContentLoaded) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(NavGraph.Auth.route) { inclusive = true }
                    }
                } else signInViewModel.loadHomeContent()
            }

            else -> {}
        }
    }

    val onGoogleSignInSuccess: (GoogleSignInAccount) -> Unit = { account ->
        signInViewModel.onGoogleSignInSuccess(account)
        signInViewModel.googleSignIn()
    }

    val onGuestSignIn: () -> Unit = {
        signInViewModel.guestSignIn()
    }

    SignInScreenContent(onGoogleSignInSuccess, onGuestSignIn)
}

@Composable
fun SignInScreenContent(
    onGoogleSignInSuccess: (GoogleSignInAccount) -> Unit = {},
    onGuestSignInClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfafafa))
    ) {
        val (
            appLogoRef,
            headlineTextRef,
            subtitleTextRef,
            googleSignInButtonRef,
            guestModeTextRef
        ) = createRefs()

        val textStartGuideline = createGuidelineFromStart(30.dp)
        val loginBtnStartGuideline = createGuidelineFromStart(0.138f)
        val loginBtnEndGuideline = createGuidelineFromEnd(0.138f)
        val loginBtnBottomGuideline = createGuidelineFromBottom(0.16f)

        Image(
            painter = painterResource(id = R.drawable.baeuja_logo),
            contentDescription = stringResource(R.string.app_logo_description),
            modifier = Modifier
                .constrainAs(appLogoRef) {
                    start.linkTo(parent.start, 30.dp)
                    top.linkTo(parent.top, 148.5.dp)
                }
                .size(43.1.dp, 39.5.dp)
        )

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.login_text),
                    )
                ) {
                    append("The most fun way\nto learn Korean,\n")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.content_text_main_color)
                    )
                ) {
                    append("BAEUJA")
                }
            },
            modifier = Modifier.constrainAs(headlineTextRef) {
                start.linkTo(textStartGuideline)
                top.linkTo(appLogoRef.bottom, 14.dp)
            },
            fontFamily = NotoSansKrFamily,
            fontSize = 24.sp,
            lineHeight = 34.sp
        )

        Text(
            text = "Learning Korean with K-Contents",
            modifier = Modifier.constrainAs(subtitleTextRef) {
                start.linkTo(textStartGuideline)
                top.linkTo(headlineTextRef.bottom, 20.dp)
            },
            fontSize = 16.sp,
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.content_text_main_color),
            lineHeight = 22.sp
        )

        GoogleLoginButton(
            Modifier
                .constrainAs(googleSignInButtonRef) {
                    start.linkTo(loginBtnStartGuideline)
                    end.linkTo(loginBtnEndGuideline)
                    bottom.linkTo(loginBtnBottomGuideline)
                },
            onGoogleSignInSuccess
        )

        Text(
            text = "Don't have an account?\nUse in guest mode",
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .constrainAs(guestModeTextRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(loginBtnBottomGuideline, 32.dp)
                }
                .clickable { onGuestSignInClick() },
            fontSize = 15.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF8f86d7),
        )
    }
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier,
    onGoogleSignInSuccess: (GoogleSignInAccount) -> Unit = {},
) {
    val context = LocalContext.current

    val googleSignInClient = getGoogleSignInClient(context)
    val signInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleGoogleSignInResult(
                data = result.data,
                onSuccess = onGoogleSignInSuccess,
                onError = { errorCode ->
                    Toast.makeText(context, "Login failed: $errorCode", Toast.LENGTH_SHORT).show()
                }
            )
        }

    Image(
        painter = painterResource(R.drawable.google_android_light_rd_ctn),
        contentDescription = "signin with google",
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                enabled = true,
                onClick = { signInLauncher.launch(googleSignInClient.signInIntent) })
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    BaujaTheme {
        SignInScreenContent()
    }
}