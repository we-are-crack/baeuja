package com.eello.baeuja.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.eello.baeuja.ui.screen.BookmarkScreen
import com.eello.baeuja.ui.screen.HomeScreen
import com.eello.baeuja.ui.screen.LearningScreen
import com.eello.baeuja.ui.screen.MyPageScreen
import com.eello.baeuja.ui.screen.ProfileInputScreen
import com.eello.baeuja.ui.screen.ReviewScreen
import com.eello.baeuja.ui.screen.SignInScreen
import com.eello.baeuja.ui.screen.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") { SplashScreen(navController) }
        authGraph(navController)
        composable("home") { HomeScreen() }
        composable("profile") { ProfileInputScreen(navController) }
        composable("learning") { LearningScreen() }
        composable("review") { ReviewScreen() }
        composable("bookmark") { BookmarkScreen() }
        composable("my") { MyPageScreen() }
    }
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(startDestination = "login", route = "auth") {
        composable("login") {
            SignInScreen(navController)
        }
        composable("join") {
            ProfileInputScreen(navController)
        }
    }
}
