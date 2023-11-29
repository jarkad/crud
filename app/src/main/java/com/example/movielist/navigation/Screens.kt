package com.example.movielist.navigation

sealed class Screens(val route: String) {
    object MoviesListScreen: Screens("movies_list")
    object PlayerPageScreen: Screens("player")
}
