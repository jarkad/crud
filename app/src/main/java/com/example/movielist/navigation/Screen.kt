package com.example.movielist.navigation

sealed class Screen {
	object MoviesListScreen : Screen()
	object SearchPageScreen : Screen()
	object CreatePageScreen : Screen()
	object ProfilePageScreen : Screen()
}
