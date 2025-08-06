package com.eello.baeuja.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.eello.baeuja.ui.screen.BookmarkScreen
import com.eello.baeuja.ui.screen.MyPageScreen
import com.eello.baeuja.ui.screen.ProfileInputScreen
import com.eello.baeuja.ui.screen.ReviewScreen
import com.eello.baeuja.ui.screen.SignInScreen
import com.eello.baeuja.ui.screen.SplashScreen
import com.eello.baeuja.ui.screen.home.HomeScreen
import com.eello.baeuja.ui.screen.learning.LearningScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        authGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(startDestination = Screen.SignIn.route, route = NavGraph.Auth.route) {
        composable(Screen.SignIn.route) { SignInScreen(navController) }
        composable(Screen.ProfileInput.route) { ProfileInputScreen(navController) }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(startDestination = Screen.Home.route, route = NavGraph.Main.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Learning.route) { LearningScreen() }
        composable(Screen.Review.route) { ReviewScreen() }
        composable(Screen.Bookmark.route) { BookmarkScreen() }
        composable(Screen.MyPage.route) { MyPageScreen() }
    }
}
