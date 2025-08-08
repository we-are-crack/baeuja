package com.eello.baeuja.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * parent 기준
 * top.linkTo(parent.top, 54.dp)
 * end.linkTo(parent.start, 30.dp)
 */
val BACK_BUTTON_TOP_MARGIN = 54.dp
val BACK_BUTTON_START_MARGIN = -(30).dp

@Composable
fun BackButton(navController: NavController?, modifier: Modifier = Modifier) {

    Icon(
        imageVector = Icons.Filled.ArrowBackIosNew,
        contentDescription = "go back",
        tint = Color(0xFF444444),
        modifier = modifier
            .clickable {
                navController?.popBackStack()
            }
    )
}