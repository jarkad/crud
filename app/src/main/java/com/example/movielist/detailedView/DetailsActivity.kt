package com.example.movielist.detailedView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movielist.detailedView.DetailedView

class DetailsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val movieId = intent.extras!!.getString("movieId", "")
			DetailedView(movieId)
		}
	}
}
