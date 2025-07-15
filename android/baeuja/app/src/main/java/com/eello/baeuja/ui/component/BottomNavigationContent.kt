package com.eello.baeuja.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.eello.baeuja.R
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.ui.theme.RobotoFamily

@Composable
fun BottomNavigationContent(
    navBackStackEntry: NavBackStackEntry?,
    onNavigate: (String) -> Unit = {}
) {
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = Modifier
            .shadow(16.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), clip = false)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        BottomNavigationItem.items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if (selected) {
                            painterResource(item.selectedIcon)
                        } else {
                            painterResource(item.unselectedIcon)
                        },
                        contentDescription = item.label,
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color(0xFF9388E8),
                    unselectedTextColor = Color.Unspecified,
                    indicatorColor = Color.Transparent
                ),
                selected = selected,
                onClick = {
                    if (!selected) {
                        onNavigate(item.route)
                    }
                }
            )
        }
    }
}

sealed class BottomNavigationItem(
    val route: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val label: String
) {
    object Learning : BottomNavigationItem(
        route = Screen.Learning.route,
        unselectedIcon = R.drawable.nav_learning_unselected,
        selectedIcon = R.drawable.nav_learning_selected,
        label = "Learning"
    )

    object Bookmark : BottomNavigationItem(
        route = Screen.Bookmark.route,
        unselectedIcon = R.drawable.nav_bookmark_unselected,
        selectedIcon = R.drawable.nav_bookmark_selected,
        label = "Bookmark"
    )

    object Home : BottomNavigationItem(
        route = Screen.Home.route,
        unselectedIcon = R.drawable.nav_home_unselected,
        selectedIcon = R.drawable.nav_home_selected,
        label = "Home"
    )

    object Review : BottomNavigationItem(
        route = Screen.Review.route,
        unselectedIcon = R.drawable.nav_review_unselected,
        selectedIcon = R.drawable.nav_review_selected,
        label = "Review"
    )

    object My : BottomNavigationItem(
        route = Screen.MyPage.route,
        unselectedIcon = R.drawable.nav_my_unselected,
        selectedIcon = R.drawable.nav_my_selected,
        label = "My"
    )

    companion object {
        val items = listOf(Learning, Bookmark, Home, Review, My)
    }
}

@Preview
@Composable
fun PreviewBottomNavigationContent() {
    BaujaTheme {
        BottomNavigationContent(null)
    }
}