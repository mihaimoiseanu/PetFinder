package com.example.petfinder.ui.screens

import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petfinder.ui.navigation.DetailsDirection
import com.example.petfinder.ui.navigation.ListDirection
import com.example.petfinder.ui.navigation.LoadingDirection
import com.example.petfinder.ui.screens.details.DetailsScreen
import com.example.petfinder.ui.screens.list.ListScreen
import com.example.petfinder.ui.screens.loading.LoadingScreen

@Composable
fun PetNavHost(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = LoadingDirection.route,
        enterTransition = { slideInHorizontally { fullWidth -> fullWidth } },
        exitTransition = { slideOutHorizontally { fullWidth -> -fullWidth / 2 } + fadeOut() },
        popEnterTransition = { slideInHorizontally { fullWidth -> -fullWidth } },
        popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } + fadeOut() }
    ) {
        composable(LoadingDirection.route) { LoadingScreen() }
        composable(ListDirection.route) { ListScreen() }
        composable(
            route = DetailsDirection.route,
            arguments = DetailsDirection.arguments
        ) {
            DetailsScreen()
        }
    }
}