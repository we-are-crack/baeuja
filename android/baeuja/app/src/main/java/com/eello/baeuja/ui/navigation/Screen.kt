package com.eello.baeuja.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    // Auth Graph
    object SignIn : Screen("${NavGraph.Auth.route}/sign_in")
    object ProfileInput : Screen("${NavGraph.Auth.route}/profile_input")

    // Main Graph
    object Home : Screen("${NavGraph.Main.route}/home")
    object Learning : Screen("${NavGraph.Main.route}/learning")
    object LearningItemDetailInfo :
        Screen("${NavGraph.Learn.route}/learning_item_detail_info/{itemId}") {
        fun createRoute(itemId: Int) = "${NavGraph.Learn.route}/learning_item_detail_info/$itemId"
    }
    object Review : Screen("${NavGraph.Main.route}/review")
    object Bookmark : Screen("${NavGraph.Main.route}/bookmark")
    object MyPage : Screen("${NavGraph.Main.route}/my_page")
}