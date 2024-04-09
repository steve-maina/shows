import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shows.R

@Composable
fun ShowsApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var showBottomBar = if(navBackStackEntry?.destination?.route == Screens.Detail().route) false else true
    val currentDestination = items.find{
        it.route == (navBackStackEntry?.destination?.route ?: Screens.Home().route)
    }
    Log.d("Logging","Route visible ${currentDestination?.route}")
    var selectedItem by remember { mutableStateOf(0) }
    selectedItem = items.indexOf(currentDestination)


    Scaffold(
        bottomBar = {
            if(showBottomBar){
                BottomBar(selectedItem, items) { screen ->
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true

                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                    selectedItem = items.indexOf(screen)
                }
            }
        }
    ){innerPadding->
        NavHost(navController, startDestination =  Screens.Home().route, Modifier.padding(innerPadding)) {
            composable(Screens.Home().route) {
                Button(onClick = { navController.navigate(Screens.Detail().route) }) {
                    Text("Go to detail View")
                }
            }
            composable(Screens.Profile().route) { Text(text= "Profile") }
            composable(Screens.Detail().route) {
                Text("Detail View")
            }
        }

    }
}
