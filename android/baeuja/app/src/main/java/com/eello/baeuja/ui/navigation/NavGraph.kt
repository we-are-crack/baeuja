package com.eello.baeuja.ui.navigation

sealed class NavGraph(val route: String) {
    object Auth : NavGraph("auth")
    object Main : NavGraph("main")
    object Learn : NavGraph("learn")
}