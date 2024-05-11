package com.example.shows.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shows.R
import com.example.shows.data.local.Show
import com.example.shows.data.network.response.SearchShow
import com.example.shows.ui.components.ScaffoldBackAppBar

import com.example.shows.ui.screens.HomeUiState
import com.example.shows.ui.screens.details.DetailPage
import com.example.shows.ui.components.SearchBox
import com.example.shows.ui.components.ShowSearchResult
import com.example.shows.ui.screens.favorites.Favorites


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsApp(
    navController: NavHostController,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier){

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
                Favorites(
                    setScaffoldAppBar = viewModel::setScaffoldAppBar,
                    episodeState = viewModel.episodeState,
                    getEpisode = viewModel::getEpisode,
                    deleteShow = viewModel::deleteShow,
                    favoritesList = viewModel.favorites
                )
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
    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            Home(
                goToDetailsPage = {
                    navController.navigate("detailPage")
                },
                setScaffoldAppBar = viewModel::setScaffoldAppBar,
                changeCurrentShow = viewModel::changeCurrentShow,
                changeTheme = viewModel::changeTheme,
                isDarkTheme = isDarkTheme,
                deleteShow = viewModel::deleteShow,
                episodeState = viewModel.episodeState,
                getEpisode = viewModel::getEpisode,
                uiState = viewModel.homeUiState,
                onValueChange = viewModel::onValueChange,
                saveShow = viewModel::saveShow,
                favorites = favorites,
                doSearch = viewModel::searchShows,
                loadingState = viewModel.loadingState
            )
        }
        composable("detailPage") {
            DetailPage(
                currentShow = viewModel.currentShow,
                showSummary = viewModel.currentShow?.summary ?: "",
                showUrl = viewModel.currentShow?.image?.medium,
                favorites = favorites,
                onClickFollow = viewModel::onClickFollow,
                viewModel = viewModel,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(
    goToDetailsPage: () -> Unit,
    setScaffoldAppBar: (String) -> Unit,
    changeCurrentShow: (SearchShow) -> Unit,
    changeTheme: (Boolean) -> Unit,
    isDarkTheme:Boolean,
    episodeState: String,
    getEpisode: (Int) -> Unit,
    uiState: HomeUiState,
    onValueChange:(String) -> Unit,
    saveShow: (Int, String)-> Unit,
    deleteShow: (Show)-> Unit,
    favorites: List<Show>,
    doSearch: () -> Unit,
    loadingState: LoadingStates,
    modifier: Modifier = Modifier) {
   setScaffoldAppBar("home")
    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)) {

        SearchBox(
            searchTerm = uiState.searchTerm,
            onValueChange = onValueChange,
            doSearch = doSearch,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
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
        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)){

            when(loadingState) {
                is LoadingStates.Success ->{
                    items(uiState.results){ searchShow ->
                        ShowSearchResult(
                            onClickItem = goToDetailsPage,
                            changeCurrentShow = changeCurrentShow,
                            deleteShow = deleteShow,
                            inDb = favorites.find { it.id == searchShow.show?.id } != null,
                            show = searchShow.show!!,
                            saveShow = saveShow
                        )
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



fun popFromInner(navController: NavHostController){
    if(navController.previousBackStackEntry != null) navController.popBackStack()
}