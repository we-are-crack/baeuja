import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eello.baeuja.ui.AppNavigation
import com.eello.baeuja.ui.component.BottomNavigationContent
import com.eello.baeuja.ui.component.OneButtonBottomBar

@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            when(currentRoute) {
                "splash", "login" -> {}

                "join" -> {
                    OneButtonBottomBar(onClick = {
                        Toast.makeText(
                            context, "click button",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
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