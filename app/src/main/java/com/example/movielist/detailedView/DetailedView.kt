package com.example.movielist.detailedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.R
import com.example.movielist.data.MovieRepository


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
                .background(colorResource(R.color.bleak_yellow_light))
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Name(name = movie!!.name)

            MyDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    if (movie!!.budget != null) {
                        Budget(budget = movie!!.budget!!)
                    }

                    if (movie!!.releaseDate != null) {
                        ReleaseDate(releaseDate = movie!!.releaseDate!!)
                    }
                }

                if (movie!!.rating != null) {
                    Rating(rating = movie!!.rating!!)
                }
            }

            MyDivider()

            if (movie!!.description != null) {
                Description(description = movie!!.description!!)
            }

            Spacer(Modifier.height(10.dp))

            if (!movie!!.actors.isNullOrEmpty()) {
                Actors(actors = movie!!.actors!!)
            }

            Image(
                painterResource(R.drawable.cinema),
                stringResource(id = R.string.cinema_icon_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp)
            )
        }
    }
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
private fun Rating(rating: Double) {
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
