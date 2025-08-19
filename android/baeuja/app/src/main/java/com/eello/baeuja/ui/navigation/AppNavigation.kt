package com.eello.baeuja.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.ui.screen.BookmarkScreen
import com.eello.baeuja.ui.screen.MyPageScreen
import com.eello.baeuja.ui.screen.ProfileInputScreen
import com.eello.baeuja.ui.screen.ReviewScreen
import com.eello.baeuja.ui.screen.SignInScreen
import com.eello.baeuja.ui.screen.SplashScreen
import com.eello.baeuja.ui.screen.home.HomeScreen
import com.eello.baeuja.ui.screen.learning.detail.LearningContentDetailScreen
import com.eello.baeuja.ui.screen.learning.main.LearningMainScreen
import com.eello.baeuja.ui.screen.learning.more.LearningContentMoreScreen
import com.eello.baeuja.ui.screen.learning.unit.ContentUnitOverviewScreen

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
        learnGraph(navController)
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
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Review.route) { ReviewScreen() }
        composable(Screen.Bookmark.route) { BookmarkScreen() }
        composable(Screen.MyPage.route) { MyPageScreen() }
    }
}

fun NavGraphBuilder.learnGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.LearningMain.route, route = NavGraph.Learn.route
    ) {
        composable(Screen.LearningMain.route) { LearningMainScreen(navController) }

        composable(
            route = Screen.LearningContentDetail.route,
            arguments = listOf(navArgument("contentId") { type = NavType.LongType })
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getLong("contentId") ?: -1
            LearningContentDetailScreen(
                navController = navController,
                contentId = contentId
            )
        }

        composable(
            route = Screen.LearningContentMore.route,
            arguments = listOf(
                navArgument("classification") {
                    type = NavType.StringType
                    defaultValue = ""  // 선택적 파라미터라면 기본값 설정 가능
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val classification = backStackEntry.arguments?.getString("classification") ?: ""
            LearningContentMoreScreen(
                navController = navController,
                classification = Classification.valueOf(classification)
            )
        }

        composable(
            route = Screen.LearningContentUnitOverview.route,
            arguments = listOf(navArgument("contentId") { type = NavType.LongType })
        ) {
            val contentId = it.arguments?.getLong("contentId") ?: -1
            ContentUnitOverviewScreen(
                navController = navController,
                contentId = contentId
            )
        }
    }
}
