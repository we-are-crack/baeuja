package com.eello.baeuja.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.eello.baeuja.ui.navigation.Screen
import com.eello.baeuja.ui.theme.BaujaTheme

@Composable
fun BottomNavigationContent(
    navBackStackEntry: NavBackStackEntry?,
    onNavigate: (String) -> Unit = {}
) {
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar (
        modifier = Modifier
            .shadow(16.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), clip = false)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            BottomNavItem("Learning", Icons.Filled.School, Screen.Learning.route),
            BottomNavItem("Bookmark", Icons.Filled.Bookmark, Screen.Bookmark.route),
            BottomNavItem("Home", Icons.Filled.Home, Screen.Bookmark.route),
            BottomNavItem("Review", Icons.Filled.RateReview, Screen.Review.route),
            BottomNavItem("My", Icons.Filled.Person, Screen.MyPage.route),
        )
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
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

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Preview
@Composable
fun PreviewBottomNavigationContent() {
    BaujaTheme {
        BottomNavigationContent(null)
    }
}