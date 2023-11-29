package com.example.movielist.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.R
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie

val jostFont = FontFamily(Font(R.font.jost_regular))

@Composable
fun MoviesList(
	viewModel: MovieListViewModel = MovieListViewModel(MovieRepository()),
	onMovieClick: (String) -> Unit = {}
) {
	val movies by viewModel.moviesLiveData.observeAsState()

	if (!movies.isNullOrEmpty()) {
		LazyColumn(
			modifier = Modifier
				.fillMaxHeight()
				.padding(0.dp, 0.dp, 0.dp, 90.dp)
		) {

			item {
				Image(
					painterResource(R.drawable.banner),
					stringResource(id = R.string.banner_icon),
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.fillMaxWidth()
						.padding(0.dp, 20.dp)
						.border(0.dp, Color.Black, RoundedCornerShape(20.dp))
				)
			}

			items(items = movies!!.toList(), itemContent = { item ->

				Row(
					modifier = Modifier
						.fillMaxWidth()
				) {
					MovieItem(
						movie = item, onMovieClick
					)
				}
			})

		}
	}
}

@Composable
fun MovieItem(movie: Movie, onMovieClick: (String) -> Unit) {
	ElevatedCard(
		modifier = Modifier
			.padding(12.dp)
			.fillMaxWidth(),
		colors = CardDefaults.cardColors(
			containerColor = Color.White, //Card background color
			contentColor = Color.DarkGray  //Card content color,e.g.text
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 6.dp
		),
	)
	{

		Column(
			modifier =
			Modifier
				.fillMaxWidth()
				.clickable {
					onMovieClick(movie.id)
				}
		) {
			Box(modifier = Modifier.fillMaxWidth()) {
				Image(
					painterResource(R.drawable.banner),
					stringResource(id = R.string.banner_icon),
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.fillMaxWidth()
				)
			}
			Column(modifier = Modifier.padding(12.dp)) {
				MovieItemName(name = movie.name)
				if (movie.description.isNotEmpty())
					MovieItemDesc(desc = movie.description)
			}

		}
	}
}

@Composable
private fun MovieItemName(name: String) {
	Text(
		text = name,
		fontSize = 21.sp,
		fontFamily = jostFont,
		textAlign = TextAlign.Left,
		fontWeight = FontWeight.Bold,
		modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
	)
}

@Composable
private fun MovieItemDesc(desc: String) {
	Text(
		text = desc,
		maxLines = 2,
		overflow = TextOverflow.Ellipsis,
		color = Color.Gray,
		fontSize = 18.sp,
		fontFamily = jostFont,
		textAlign = TextAlign.Left
	)
}
