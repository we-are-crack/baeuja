package com.eello.baeuja.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("${NavGraph.Auth.route}/sign_in")
    object ProfileInput : Screen("${NavGraph.Auth.route}/profile_input")
    object Home : Screen("home")
    object Learning : Screen("learning")
    object Review : Screen("review")
    object Bookmark : Screen("bookmark")
    object MyPage : Screen("my_page")
}