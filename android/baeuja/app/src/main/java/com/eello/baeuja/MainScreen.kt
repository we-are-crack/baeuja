import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eello.baeuja.ui.component.BottomNavigationContent
import com.eello.baeuja.ui.navigation.AppNavigation
import com.eello.baeuja.ui.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            when (currentRoute) {
                Screen.Splash.route,
                Screen.SignIn.route,
                Screen.ProfileInput.route,
                Screen.LearningItemDetailInfo.route -> {
                }

                else -> {
                    BottomNavigationContent(
                        navBackStackEntry = navBackStackEntry,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                // 중복 스택 방지 및 상태 복원
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}