package com.example.petfinder.ui.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

object DirectionBack : NavigationDirection {
    override val route: String = "back"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val navBuilder: NavOptionsBuilder.() -> Unit = {}
}

object LoadingDirection : NavigationDirection {
    override val route: String = "loading"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val navBuilder: NavOptionsBuilder.() -> Unit = {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }

    override fun toString(): String = route
}

object ListDirection : NavigationDirection {
    override val route: String = "list"
    override val arguments: List<NamedNavArgument> = emptyList()
    override val navBuilder: NavOptionsBuilder.() -> Unit = {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }

    override fun toString(): String = route
}

//Try to find a better solution
data class DetailsDirection(val petId: Int) : NavigationDirection {

    override val route: String = Companion.route.replace("{$PET_ID}", petId.toString())
    override val navBuilder: NavOptionsBuilder.() -> Unit = Companion.navBuilder
    override val arguments: List<NamedNavArgument> = Companion.arguments

    companion object : NavigationDirection {
        private const val PET_ID = "id"
        override val route: String = "pet/{$PET_ID}"
        override val arguments: List<NamedNavArgument> =
            listOf(navArgument(PET_ID) { type = NavType.IntType })
        override val navBuilder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true }
        fun getPetId(stateHandle: SavedStateHandle): Int = checkNotNull(stateHandle[PET_ID])
    }
}
