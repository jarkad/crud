package com.example.movielist.profile

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import com.example.movielist.edit.EditActivity

@Composable
fun ProfileScreen(
	viewModel: ProfileScreenViewModel = ProfileScreenViewModel(MovieRepository()),
	context: Context,
) {
	val movies by viewModel.movies.observeAsState()

	Column(Modifier.fillMaxWidth()) {
		Text(
			modifier = Modifier
				.align(CenterHorizontally)
				.padding(20.dp),
			text = "PROFILE",
		)
		Row {
			Icon(Icons.Default.Person, null)
			Column {
				Text("John Doe", fontSize = 16.sp)
				Text("noreply@gmail.com")
			}
		}
		Text(
			modifier = Modifier
				.align(CenterHorizontally)
				.padding(20.dp),
			text = "MY PRODUCTS",
		)
		if (!movies.isNullOrEmpty()) {
			LazyColumn(Modifier.fillMaxWidth()) {
				items(items = movies!!.toList()) { movie ->
					Item(movie, context)
				}
			}
		}
	}
}

@Composable
private fun Item(movie: Movie, context: Context) {
	ElevatedCard {
		Row {
			Icon(Icons.Default.Info, null)
			Column(Modifier.fillMaxWidth()) {
				Text(movie.name)
				Text(movie.description)
				Row {
					Button(onClick = {
						context.startActivity(
							Intent(
								context,
								EditActivity::class.java
							).putExtra("movieId", movie.id)
						)
					}) { Text("EDIT") }
					Button(onClick = { /*TODO*/ }) { Text("DELETE") }
				}
			}
		}
	}
}
