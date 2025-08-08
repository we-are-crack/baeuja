package com.eello.baeuja.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eello.baeuja.R
import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.ui.component.OneButtonBottomBar
import com.eello.baeuja.ui.navigation.NavGraph
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.NotoSansKrFamily
import com.eello.baeuja.ui.theme.RobotoFamily
import com.eello.baeuja.viewmodel.DisplayNameAvailable
import com.eello.baeuja.viewmodel.SignInViewModel
import com.eello.baeuja.viewmodel.SignUpViewModel
import timber.log.Timber

@Composable
fun ProfileInputScreen(navController: NavController) {
    val authEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(NavGraph.Auth.route)
    }
    val signInViewModel: SignInViewModel = hiltViewModel(authEntry)
    val signUpViewModel: SignUpViewModel = hiltViewModel()

    ProfileInputRoute(navController, signInViewModel, signUpViewModel)
}

@Composable
fun ProfileInputRoute(
    navController: NavController,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel
) {
    val gui by signInViewModel.googleSignInUserInfo.collectAsState()
    LaunchedEffect(gui) {
//        if (gui == null) {
//            Timber.d("GoogleSignInUserInfo 이 Null 이므로 SignInScreen 으로 이동")
//            navController.navigate(Screen.SignIn.route) {
//                popUpTo(NavGraph.Auth.route) { inclusive = true }
//            }
//        }
    }

    val displayName by signUpViewModel.displayName.collectAsState()
    val isDisplayNameAvailable by signUpViewModel.isAvailable.collectAsState()
    val signUpResult by signUpViewModel.signInResult.collectAsState()
    LaunchedEffect(signUpResult) {
        if (signUpResult == AuthResult.Success) {
            signInViewModel.loadHomeContent()
            Timber.d("회원가입 성공으로 HomeScreen 으로 이동")
            navController.navigate(Screen.Home.route) {
                popUpTo(NavGraph.Auth.route) { inclusive = true }
            }
        }
    }

    val goBack: () -> Unit = { navController.popBackStack() }
    val onDisplayNameChange: (String) -> Unit = { signUpViewModel.onDisplayNameChanged(it) }
    val signUp: () -> Unit = gui?.let { { signUpViewModel.signUp(gui!!) } } ?: {}

    ProfileInputContent(
        goBack = goBack,
        displayName = displayName,
        isDisplayNameAvailable = isDisplayNameAvailable,
        signUp = signUp,
        onDisplayNameChange = onDisplayNameChange
    )
}

@Composable
fun ProfileInputContent(
    goBack: () -> Unit = {},
    displayName: String,
    isDisplayNameAvailable: DisplayNameAvailable,
    signUp: () -> Unit,
    onDisplayNameChange: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            OneButtonBottomBar(
                enabled = isDisplayNameAvailable is DisplayNameAvailable.Available,
                onClick = { signUp() }
            )
        }
    ) { innerPadding ->
        val paddingValues = PaddingValues(
            top = 0.dp,
            bottom = 0.dp,
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFfafafa))
        ) {
            val (
                backIcon,
                welcomeText,
                guideText,
                nicknameInput,
                nicknameCheckMsg
            ) = createRefs()

            val textStartGuideLine = createGuidelineFromStart(30.dp)
            val inputFiledStartGuideLine = createGuidelineFromStart(22.dp)
            val inputFiledEndGuideLine = createGuidelineFromEnd(22.dp)

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "go back",
                tint = Color(0xFF444444),
                modifier = Modifier
                    .constrainAs(backIcon) {
                        top.linkTo(parent.top, 54.dp)
                        end.linkTo(textStartGuideLine)
                    }
                    .clickable { goBack }
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
                        border = BorderStroke(
                            1.dp,
                            color = when (isDisplayNameAvailable) {
                                DisplayNameAvailable.Idle -> Color(0xFFcccccc)
                                DisplayNameAvailable.Available -> Color(0xFF009688)
                                is DisplayNameAvailable.Unavailable -> Color.Red
                            }
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                val startPadding = 16.dp
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Nickname",
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                        color = colorResource(R.color.content_text_main_color),
                        modifier = Modifier
                            .padding(start = startPadding, top = 8.dp)
                            .height(16.dp)
                    )

                    BasicTextField(
                        value = displayName,
                        onValueChange = { onDisplayNameChange(it) },
                        modifier = Modifier
                            .padding(start = startPadding, end = startPadding)
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
                                if (displayName.isEmpty()) {
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

            if (isDisplayNameAvailable is DisplayNameAvailable.Unavailable) {
                Text(
                    text = isDisplayNameAvailable.reason,
                    color = Color.Red,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .constrainAs(nicknameCheckMsg) {
                            top.linkTo(nicknameInput.bottom, margin = 4.dp)
                            end.linkTo(nicknameInput.end)
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
        ProfileInputContent(
            goBack = {},
            displayName = "",
            isDisplayNameAvailable = DisplayNameAvailable.Unavailable("Nickname is not available"),
            signUp = {},
            onDisplayNameChange = {}
        )
    }
}