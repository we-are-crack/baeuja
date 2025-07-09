package com.eello.baeuja.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eello.baeuja.ui.screen.LoginScreen
import com.eello.baeuja.ui.screen.SplashScreen

import androidx.navigation.NavHostController
import com.eello.baeuja.ui.screen.HomeScreen
import com.eello.baeuja.ui.screen.LearningScreen
import com.eello.baeuja.ui.screen.ReviewScreen
import com.eello.baeuja.ui.screen.BookmarkScreen
import com.eello.baeuja.ui.screen.MyPageScreen
import com.eello.baeuja.ui.screen.ProfileInputScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "join",
        modifier = modifier
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("join") { ProfileInputScreen() }
        composable("home") { HomeScreen() }
        composable("learning") { LearningScreen() }
        composable("review") { ReviewScreen() }
        composable("bookmark") { BookmarkScreen() }
        composable("my") { MyPageScreen() }
    }
}

