package com.example.movielist.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import com.example.movielist.edit.EditActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = ProfileScreenViewModel(MovieRepository()),
    context: Context,
) {
    val movies by viewModel.movies.observeAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(20.dp),
            text = "PROFILE",
        )
        Row {
            Icon(Icons.Default.Person, null)
            Column(modifier = Modifier.padding(20.dp, 0.dp)) {
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
    ElevatedCard(modifier = Modifier.padding(0.dp, 20.dp)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Icon(Icons.Default.Info, null)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
            ) {
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
                    Button(
                        modifier = Modifier.padding(20.dp, 0.dp),
                        onClick = { deleteProduct(movie, context) }) { Text("DELETE") }
                }
            }
        }
    }
}

private fun deleteProduct(movie: Movie, context: Context) = CoroutineScope(Dispatchers.IO).launch {
    val db = Firebase.firestore
    val collection = db.collection("movies")

    val movieQuery = collection
        .whereEqualTo("name", movie.name)
        .get()
        .await()
    if (movieQuery.documents.isNotEmpty()) {
        for (document in movieQuery) {
            try {
                collection.document(document.id).delete().await()
            } catch (e: Exception) {
            }
        }
    } else {
        withContext(Dispatchers.Main) {
        }
    }
}

