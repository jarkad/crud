package com.example.movielist.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {
	Box(
		modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
	) {
		Text(
			fontSize = 20.sp, text = "Search will be here"
		)
	}
}
