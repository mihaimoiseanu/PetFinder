package com.example.petfinder.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavOptionsBuilder

interface NavigationDirection {
    val route: String
    val navBuilder: NavOptionsBuilder.() -> Unit
    val arguments: List<NamedNavArgument>
}
