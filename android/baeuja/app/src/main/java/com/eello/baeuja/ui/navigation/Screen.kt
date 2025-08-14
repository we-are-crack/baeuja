package com.eello.baeuja.ui.navigation

import com.eello.baeuja.domain.content.model.Classification

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    // Auth Graph
    object SignIn : Screen("${NavGraph.Auth.route}/sign_in")
    object ProfileInput : Screen("${NavGraph.Auth.route}/profile_input")

    object Home : Screen("${NavGraph.Main.route}/home")

    // Learn Graph
    object LearningMain : Screen("${NavGraph.Learn.route}/main")
    object LearningContentDetail :
        Screen("${NavGraph.Learn.route}/detail/{contentId}") {
        fun createRoute(contentId: Long) =
            "${NavGraph.Learn.route}/detail/$contentId"
    }

    object LearningContentMore :
        Screen("${NavGraph.Learn.route}/more?classification={classification}") {
        fun createRoute(classification: Classification) =
            "${NavGraph.Learn.route}/more?classification=${classification.name}"
    }

    object Review : Screen("${NavGraph.Main.route}/review")
    object Bookmark : Screen("${NavGraph.Main.route}/bookmark")
    object MyPage : Screen("${NavGraph.Main.route}/my_page")
}