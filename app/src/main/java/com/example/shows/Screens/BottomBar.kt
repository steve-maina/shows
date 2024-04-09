
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun BottomBar(selectedItem: Int, screens: List<Screens>, modifier: Modifier = Modifier, onItemClick: (Screens) -> Unit) {

    NavigationBar {
        screens.forEachIndexed { index, item ->
            Log.d("Index","index: $index - selected item:$selectedItem")
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = item.route) },
                label = { Text(item.route) },
                selected = selectedItem == index,
                onClick = { onItemClick(item) }
            )
        }
    }
//    BottomNavigation {
//        screens.forEach { screen ->
//            BottomNavigationItem(
//                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
//                label = { Text(stringResource(screen.resourceId)) },
//                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                onClick = onItemClick
//            )
//        }
//    }
}