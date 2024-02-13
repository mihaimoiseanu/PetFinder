package com.example.petfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.petfinder.ui.navigation.DirectionBack
import com.example.petfinder.ui.navigation.NavigationManager
import com.example.petfinder.ui.screens.PetNavHost
import com.example.petfinder.ui.theme.PetFinderTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetFinderTheme {
                // A surface container using the 'background' color from the theme
                Box(modifier = Modifier.statusBarsPadding()) {
                    val navController = rememberNavController()
                    LaunchedEffect(true) {
                        navigationManager.commands.collect { command ->
                            when (command) {
                                DirectionBack -> {
                                    val popped = navController.popBackStack()
                                    if (!popped) finish()
                                }

                                else -> navController.navigate(
                                    route = command.route,
                                    navOptions = navOptions(command.navBuilder)
                                )
                            }
                        }
                    }
                    PetNavHost(navController = navController)
                }
            }
        }
    }
}