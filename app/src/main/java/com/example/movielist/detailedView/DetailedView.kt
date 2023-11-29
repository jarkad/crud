package com.example.movielist.detailedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.R
import com.example.movielist.data.MovieRepository
import com.example.movielist.list.jostFont


@Composable
fun DetailedView(
	movieId: String,
	viewModel: DetailedViewModel = DetailedViewModel(movieId, MovieRepository())
) {

	val movie by viewModel.movieLiveData.observeAsState()

	if (movie != null) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.White)
				.padding(16.dp)
				.verticalScroll(
					rememberScrollState()
				)
		) {

			Image(
				painterResource(R.drawable.cinema),
				stringResource(id = R.string.cinema_icon_desc),
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.fillMaxWidth()
					.height(300.dp)
					.padding(0.dp, 20.dp)
					.border(0.dp, Color.Black, RoundedCornerShape(20.dp))
			)
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {

				Name(name = movie!!.name)
				if (movie!!.rating != null) {
					Rating(rating = movie!!.rating!!)
				}
			}


			Row(
				modifier = Modifier.fillMaxWidth()
			) {
				Button(
					modifier = Modifier
						.fillMaxWidth()
						.background(colorResource(id = R.color.purple_500)),
					onClick = { /*TODO*/ }) {
					Text(
						stringResource(id = R.string.btn_buy),
						modifier = Modifier.padding(15.dp, 5.dp),
						fontFamily = jostFont,
						color = colorResource(id = R.color.white),
						fontSize = 16.sp
					)
				}
			}



			if (movie!!.description != null) {
				Description(description = movie!!.description!!)
			}

			Spacer(Modifier.height(10.dp))

			if (!movie!!.actors.isNullOrEmpty()) {
				Actors(actors = movie!!.actors!!)
			}


		}
	}
}

@Composable
@Preview
private fun DetailedViewPreview() {
	DetailedView(movieId = "3956")
}

@Composable
private fun Name(name: String) {
	Text(
		text = name,
		color = Color.Black,
		fontSize = 26.sp,
		fontWeight = FontWeight.Bold,
		fontFamily = FontFamily.Serif,
		textAlign = TextAlign.Left
	)
}

@Composable
fun Rating(rating: Double) {
	Row(verticalAlignment = Alignment.CenterVertically) {
		Image(
			painterResource(R.drawable.baseline_star_half_24),
			stringResource(id = R.string.cinema_icon_desc),
			contentScale = ContentScale.Crop
		)

		Text(
			text = rating.toString(),
			color = Color.Black,
			fontWeight = FontWeight.Bold,
			fontSize = 23.sp,
			fontFamily = FontFamily.SansSerif,
			textAlign = TextAlign.Center
		)
	}
}

@Composable
private fun Budget(budget: Int) {
	Text(
		modifier = Modifier.padding(bottom = 3.dp),
		text = stringResource(id = R.string.detailed_view_budget_label, budget),
		color = Color.Black,
		fontSize = 15.sp,
		fontFamily = FontFamily.SansSerif
	)
}


@Composable
private fun ReleaseDate(releaseDate: String) {
	Text(
		modifier = Modifier.padding(bottom = 3.dp),
		text = stringResource(id = R.string.detailed_view_release_date_label, releaseDate),
		color = Color.Black,
		fontSize = 15.sp,
		fontFamily = FontFamily.SansSerif
	)
}

@Composable
private fun Description(description: String) {
	Text(
		modifier = Modifier.padding(top = 10.dp),
		text = description,
		color = Color.DarkGray,
		fontSize = 20.sp,
		fontFamily = FontFamily.SansSerif
	)
}


@Composable
private fun Actors(actors: List<String>) {
	Column(modifier = Modifier.fillMaxWidth()) {
		var i = 0
		for (actor in actors) {
			ActorTextView(actor = actor, ++i == actors.size)
		}
	}
}

@Composable
private fun ActorTextView(actor: String, isTheLastOne: Boolean) {
	Text(
		modifier = Modifier.padding(3.dp, 1.dp),
		text = if (isTheLastOne) actor else "$actor,",
		color = Color.DarkGray,
		fontSize = 19.sp,
		fontFamily = FontFamily.SansSerif,
		fontStyle = FontStyle.Italic
	)
}

@Composable
private fun MyDivider() {
	Divider(
		modifier = Modifier.padding(0.dp, 10.dp),
		color = Color.LightGray

	)
}
