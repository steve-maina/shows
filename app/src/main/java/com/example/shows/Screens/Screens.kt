import androidx.annotation.StringRes
import com.example.shows.R

const val HOME = "home"
const val PROFILE = "profile"
const val DETAIL = "detail"

sealed class Screens(val route: String) {
    class Home:Screens(HOME)
    class Profile: Screens(PROFILE)
    class Detail: Screens(DETAIL)
}

val items = listOf(Screens.Home(),Screens.Profile())



