package com.example.shows.ui.screens.home

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.contains
import androidx.navigation.navigation
import com.example.shows.R
import com.example.shows.data.local.Show

import com.example.shows.ui.screens.HomeUiState
import com.example.shows.ui.screens.components.DetailPage
import com.example.shows.ui.screens.components.Favorite
import com.example.shows.ui.screens.components.Favorites
import com.example.shows.ui.screens.components.SearchShow
import items
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsApp(
    navController: NavHostController,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier){

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val appBarToShow = viewModel.appBarToShow
    Scaffold(
        topBar = {
            AnimatedContent(
                targetState = appBarToShow.value,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(300,easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.Up).togetherWith(slideOutOfContainer(
                            animationSpec = tween(300, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.Down
                        ))
                }
            ) { it ->
                when (it) {
                    is ScaffoldAppBar.FavoriteAppBar -> {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                text = stringResource(id = R.string.favorites),
                                style = MaterialTheme.typography.headlineLarge
                            )
                        })
                    }

                    is ScaffoldAppBar.BackAppBar -> {
                        ScaffoldBackAppBar(
                            onClick = {
                                viewModel.popFromInnerBackStack.value = true
                            },
                            title = viewModel.detailPagesTitle.value
                        )
                    }

                    is ScaffoldAppBar.BrandAppBar -> {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = MaterialTheme.typography.displayLarge
                            )
                        })
                    }

                    else -> {

                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomBarScreens.forEach { screen ->
                    val selected = currentDestination?.route == screen.route
                    NavigationBarItem(
                        icon = {
                            Icon(painter = painterResource(screen.iconRes), contentDescription =  screen.route)
                        },
                        onClick ={
                                 navController.navigate(screen.route) {
                                     popUpTo(navController.graph.findStartDestination().id) {
                                         saveState = true
                                     }
                                     launchSingleTop = true
                                     restoreState = true
                                 }
                        },
                        selected = selected,
                        )
                }

            }
        }
    ) {
        NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(it) ){
            composable("home"){
                HomeNavHost(viewModel = viewModel)
            }
            composable("favorites") {
                Favorites(viewModel = viewModel, episodeState = viewModel.episodeState, getEpisode = viewModel::getEpisode, deleteShow = viewModel::deleteShow,favoritesList =viewModel.favorites)
            }
        }
    }
}

@Composable
fun HomeNavHost(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    if(viewModel.popFromInnerBackStack.value) {
        viewModel.popFromInnerBackStack.value= false
        navController.navigateUp()
    }
//    if(viewModel.homeNavigateBack.value == true){
//        navController.navigateUp()
//        viewModel.homeNavigateBack.value = false
//    }
    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            Home(
                {
                    navController.navigate("detailPage")
                },
                viewModel,
                viewModel::changeTheme,
                isDarkTheme,
                viewModel::deleteShow,
                viewModel.episodeState,
                viewModel::getEpisode,
                viewModel.homeUiState,
                viewModel::onValueChange,
                viewModel::saveShow,
                viewModel::deleteShow,
                favorites,
                viewModel::searchShows,
                viewModel.loadingState
            )
        }
        composable("detailPage") {
            DetailPage(
                currentShow = viewModel.currentShow,
                showSummary = viewModel.currentShow?.summary ?: "",
                showUrl = viewModel.currentShow?.image?.medium,
                favorites = favorites,
                onClickFollow = viewModel::onClickFollow,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(
    goToDetailsPage: () -> Unit,
    viewModel: HomeViewModel,
    changeTheme: (Boolean) -> Unit,
    isDarkTheme:Boolean,
    getShow: (Show)-> Unit,
    episodeState: String,
    getEpisode: (Int) -> Unit,
    uiState: HomeUiState, onValueChange:(String) -> Unit,
    saveShow: (Int, String)-> Unit,
    deleteShow: (Show)-> Unit,
    favorites: List<Show>,
    doSearch: () -> Unit,
    loadingState: LoadingStates,
    modifier: Modifier = Modifier) {
    viewModel.setScaffoldAppBar("home")
    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)) {
        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            value = uiState.searchTerm,
            onValueChange = {
                onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.search), contentDescription = null)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    doSearch()
                    keyboardController?.hide()
                }
            )
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically ,modifier = Modifier.fillMaxWidth()){
            Text("Dark Theme?")
            Switch(checked = isDarkTheme , onCheckedChange = {darkTheme->
                changeTheme(darkTheme)
            })

        }
        if(loadingState == LoadingStates.Loading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            return
        }
        LazyColumn(modifier = Modifier.fillMaxSize()){

            when(loadingState) {
                is LoadingStates.Success ->{
                    items(uiState.results){ searchShow ->
                        SearchShow(
                            goToDetailsPage,
                            viewModel::changeCurrentShow,deleteShow, favorites.find { it.id == searchShow.show?.id } != null, show = searchShow.show!!,saveShow)
                    }
                }
                is LoadingStates.Loading ->{

                }
                is LoadingStates.Error -> {
                    item {
                        Text("Network Error")
                    }
                }

            }
        }
    }
}

sealed class Screens(
    val route: String,
    @DrawableRes val iconRes: Int
) {
    object Home: Screens(
        route = "home",
        iconRes = R.drawable.home
    )
    object Favorites: Screens(
        route = "favorites",
        iconRes = R.drawable.favorite
    )
}

val bottomBarScreens = listOf(
    Screens.Home,
    Screens.Favorites
)

sealed class ScaffoldAppBar {
    object BrandAppBar: ScaffoldAppBar()
    object FavoriteAppBar: ScaffoldAppBar()
    object BackAppBar: ScaffoldAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBackAppBar(onClick: () -> Unit, title: String) {
    TopAppBar(title = {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Filled.ArrowBack,contentDescription = "Go back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
            )
        }
    })

}

fun popFromInner(navController: NavHostController){
    if(navController.previousBackStackEntry != null) navController.popBackStack()
}