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
import com.eello.baeuja.ui.screen.BookmarkScreen
import com.eello.baeuja.ui.screen.MyPageScreen
import com.eello.baeuja.ui.screen.ProfileInputScreen
import com.eello.baeuja.ui.screen.ReviewScreen
import com.eello.baeuja.ui.screen.SignInScreen
import com.eello.baeuja.ui.screen.SplashScreen
import com.eello.baeuja.ui.screen.home.view.HomeScreen
import com.eello.baeuja.ui.screen.learning.LearningItemInfo
import com.eello.baeuja.ui.screen.learning.LearningScreen
import com.eello.baeuja.ui.screen.learning.MoreItemScreen
import com.eello.baeuja.viewmodel.ContentClassification

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
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Review.route) { ReviewScreen() }
        composable(Screen.Bookmark.route) { BookmarkScreen() }
        composable(Screen.MyPage.route) { MyPageScreen() }
    }
}

fun NavGraphBuilder.learnGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Learning.route, route = NavGraph.Learn.route
    ) {
        composable(Screen.Learning.route) { LearningScreen(navController) }

        composable(
            route = Screen.LearningItemDetailInfo.route,
            arguments = listOf(navArgument("itemId") { type = NavType.LongType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: -1
            LearningItemInfo(
                navController = navController,
                itemId = itemId
            )
        }

        composable(
            route = Screen.LearningItemMore.route,
            arguments = listOf(
                navArgument("classification") {
                    type = NavType.StringType
                    defaultValue = ""  // 선택적 파라미터라면 기본값 설정 가능
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val classification = backStackEntry.arguments?.getString("classification") ?: ""
            MoreItemScreen(
                navController = navController,
                classification = ContentClassification.valueOf(classification)
            )
        }
    }
}
