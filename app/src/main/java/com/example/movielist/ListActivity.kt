package com.example.movielist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.movielist.addNew.AddNewMovie
import com.example.movielist.detailedView.DetailsActivity
import com.example.movielist.list.MoviesList
import com.example.movielist.navigation.Screen
import com.example.movielist.profile.ProfileScreen
import com.example.movielist.search.SearchScreen
import com.example.movielist.ui.theme.MovieAppTheme
import com.google.firebase.FirebaseApp


class ListActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		FirebaseApp.initializeApp(this)
		setContent {
			MovieAppTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
				) {
					var screen: Screen by remember { mutableStateOf(Screen.MoviesListScreen) }
					Scaffold(bottomBar = {
						NavigationBar {
							NavigationBarItem(
								selected = screen == Screen.MoviesListScreen,
								onClick = { screen = Screen.MoviesListScreen },
								icon = { Icon(Icons.Default.Home, contentDescription = null) },
								label = { Text("Home") },
							)
							NavigationBarItem(
								selected = screen == Screen.SearchPageScreen,
								onClick = { screen = Screen.SearchPageScreen },
								icon = { Icon(Icons.Default.Search, contentDescription = null) },
								label = { Text("Search") },
							)
							NavigationBarItem(
								selected = screen == Screen.CreatePageScreen,
								onClick = { screen = Screen.CreatePageScreen },
								icon = { Icon(Icons.Default.Add, contentDescription = null) },
								label = { Text("Create") },
							)
							NavigationBarItem(
								selected = screen == Screen.ProfilePageScreen,
								onClick = { screen = Screen.ProfilePageScreen },
								icon = { Icon(Icons.Default.Person, contentDescription = null) },
								label = { Text("Profile") },
							)
						}
					}) { paddingValues ->
						val ctx = this
						Box(modifier = Modifier.padding(paddingValues)) {
							when (screen) {
								Screen.MoviesListScreen -> {
									MoviesList { movieId ->
										ctx.startActivity(
											Intent(
												ctx,
												DetailsActivity::class.java
											).putExtra(
												"movieId",
												movieId
											)
										)
									}
								}

								Screen.SearchPageScreen -> {
									SearchScreen()
								}

								Screen.CreatePageScreen -> {
									AddNewMovie()
								}

								Screen.ProfilePageScreen -> {
									ProfileScreen()
								}
							}
						}
					}
				}
			}
		}
	}
}
