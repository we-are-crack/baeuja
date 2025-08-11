package com.eello.baeuja.ui.navigation

import com.eello.baeuja.viewmodel.ContentClassification

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    // Auth Graph
    object SignIn : Screen("${NavGraph.Auth.route}/sign_in")
    object ProfileInput : Screen("${NavGraph.Auth.route}/profile_input")

    object Home : Screen("${NavGraph.Main.route}/home")

    // Learn Graph
    object Learning : Screen("${NavGraph.Learn.route}/main")
    object LearningItemDetailInfo :
        Screen("${NavGraph.Learn.route}/detail_info/{itemId}") {
        fun createRoute(itemId: Long) =
            "${NavGraph.Learn.route}/detail_info/$itemId"
    }

    object LearningItemMore :
        Screen("${NavGraph.Learn.route}/more?classification={classification}") {
        fun createRoute(classification: ContentClassification) =
            "${NavGraph.Learn.route}/more?classification=${classification.name}"
    }

    object Review : Screen("${NavGraph.Main.route}/review")
    object Bookmark : Screen("${NavGraph.Main.route}/bookmark")
    object MyPage : Screen("${NavGraph.Main.route}/my_page")
}