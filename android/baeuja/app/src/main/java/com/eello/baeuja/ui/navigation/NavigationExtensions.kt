package com.eello.baeuja.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateToContentUnits(contentId: Long) {
    navigate(Screen.LearningContentUnitOverview.createRoute(contentId = contentId))
}