package com.example.movielist.addNew

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movielist.ListActivity
import com.example.movielist.R
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import com.example.movielist.list.jostFont
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale.Category


@Composable
fun AddNewMovie(
    viewModel: AddNewMovieViewModel = AddNewMovieViewModel(MovieRepository())
) {
    val localContext = LocalContext.current

    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val condition = remember { mutableStateOf("") }
    val color = remember { mutableStateOf("") }
    val categoryOptions = listOf("Professional", "Beginner", "Amateur")
    val isCategoryExpanded = remember {
        mutableStateOf(false)
    }
    val category = remember { mutableStateOf("") }
    val selectedCategoryText = remember { mutableStateOf(categoryOptions[0]) }

    val ratingOptions = listOf("1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5")
    val isRatingExpanded = remember {
        mutableStateOf(false)
    }
    val selectedRatingText = remember { mutableStateOf(ratingOptions[0]) }

    val response by viewModel.insertResponseLiveData.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CreateNewMoviePageTitle()
            Spacer(modifier = Modifier.height(15.dp))
            NameInput(name = name.value, onNameChange = { name.value = it })
            Spacer(Modifier.height(16.dp))
            DescriptionInput(description = description.value,
                onDescriptionChange = { description.value = it })
            Spacer(modifier = Modifier.height(15.dp))
            PriceInput(price = price.value, onPriceChanged = { price.value = it })
            Spacer(modifier = Modifier.height(15.dp))
            ConditionInput(condition = condition.value, onConditionChange = { condition.value = it })
            Spacer(modifier = Modifier.height(15.dp))
            ColorInput(color = color.value, onColorChange = { color.value = it })
            Spacer(modifier = Modifier.height(15.dp))
            Category(
                isExpanded = isCategoryExpanded.value,
                onExpandedChanged = { isCategoryExpanded.value = !isCategoryExpanded.value },
                selectedOptionText = selectedCategoryText.value,
                onSelectedOptionChanged = { selectedCategoryText.value = it },
                options = categoryOptions
            )
            Spacer(modifier = Modifier.height(15.dp))
            Rating(
                isExpanded = isRatingExpanded.value,
                onExpandedChanged = { isRatingExpanded.value = !isRatingExpanded.value },
                selectedOptionText = selectedRatingText.value,
                onSelectedOptionChanged = { selectedRatingText.value = it },
                options = ratingOptions
            )

            // actors text input - comma separated 4x

            Spacer(Modifier.height(16.dp))
            AddNewButton {
                val constructedMovie: Movie? = constructMovieIfInputValid(
                    nameInput = name.value,
                    descriptionInput = description.value,
                    priceInput = price.value,
                    conditionInput = condition.value,
                    categoryInput = selectedCategoryText.value,
                    colorInput = color.value,
                    ratingInput = selectedRatingText.value,
                    context = localContext
                )

                if (constructedMovie != null
                ) {
                    viewModel.saveNewMovie(
                        constructedMovie
                    )
                }
            }
        }

        if (response != null) {
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.Center),
                fontSize = 19.sp,
                text = if (response!!.status == "OK") stringResource(id = R.string.saved_success_msg)
                else stringResource(id = R.string.saved_fail_msg)
            )

            if (response!!.status == "OK") {
                localContext.startActivity(Intent(localContext, ListActivity::class.java))
            }
        }
    }

}

@Composable
private fun CreateNewMoviePageTitle() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id =R.string.title_activity_add_new_product),
        color = Color.Black,
        fontSize = 26.sp,
        fontFamily = jostFont,
        textAlign = TextAlign.Center
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NameInput(name: String, onNameChange: (String) -> Unit) {
    TextField(modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black, containerColor = colorResource(id = R.color.bleak_yellow_light)
        ),
        value = name,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { onNameChange(it) },
        label = {
            Text(stringResource(id = R.string.product_name_input_hint))
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DescriptionInput(description: String, onDescriptionChange: (String) -> Unit) {
    TextField(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black, containerColor = colorResource(id = R.color.bleak_yellow_light)
        ),
        value = description,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { onDescriptionChange(it) },
        label = {
            Text(stringResource(id = R.string.product_desc_input_hint))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PriceInput(price: String, onPriceChanged: (String) -> Unit) {
    TextField(modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black, containerColor = colorResource(id = R.color.bleak_yellow_light)
        ),
        value = price,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { onPriceChanged(it) },
        label = {
            Text(stringResource(id = R.string.product_price_input_hint))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColorInput(color: String, onColorChange: (String) -> Unit) {
    TextField(modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black, containerColor = colorResource(id = R.color.bleak_yellow_light)
        ),
        value = color,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { onColorChange(it) },
        label = {
            Text(stringResource(id = R.string.product_color_input_hint))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConditionInput(condition: String, onConditionChange: (String) -> Unit) {
    TextField(modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black, containerColor = colorResource(id = R.color.bleak_yellow_light)
        ),
        value = condition,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { onConditionChange(it) },
        label = {
            Text(stringResource(id = R.string.product_condition_input_hint))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Category(
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    selectedOptionText: String,
    onSelectedOptionChanged: (String) -> Unit,
    options: List<String>
) {
    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
        onExpandedChanged(it)
    }) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(stringResource(id = R.string.product_category_menu_hint)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = colorResource(id = R.color.bleak_yellow_light)
            )
        )

        ExposedDropdownMenu(modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = {
                onExpandedChanged(false)
            }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    onSelectedOptionChanged(selectionOption)
                    onExpandedChanged(false)
                }, text = { Text(text = selectionOption) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Rating(
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    selectedOptionText: String,
    onSelectedOptionChanged: (String) -> Unit,
    options: List<String>
) {
    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
        onExpandedChanged(it)
    }) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(stringResource(id = R.string.product_rating_menu_hint)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = colorResource(id = R.color.bleak_yellow_light)
            )
        )

        ExposedDropdownMenu(modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = {
                onExpandedChanged(false)
            }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    onSelectedOptionChanged(selectionOption)
                    onExpandedChanged(false)
                }, text = { Text(text = selectionOption) })
            }
        }
    }
}

@Composable
private fun AddNewButton(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.purple_500), contentColor = Color.White
        )

    ) {
        Text(
            fontSize = 17.sp, text = stringResource(id = R.string.save_btn_text)
        )
    }
}

private fun constructMovieIfInputValid(
    nameInput: String?,
    descriptionInput: String?,
    priceInput: String?,
    categoryInput: String?,
    colorInput: String?,
    conditionInput: String?,
    ratingInput: String?,
    context: Context
): Movie? {
    if (nameInput.isNullOrEmpty() ||
        descriptionInput.isNullOrEmpty() ||
        priceInput.isNullOrEmpty() ||
        categoryInput.isNullOrEmpty() ||
        colorInput.isNullOrEmpty() ||
        conditionInput.isNullOrEmpty () ||
        ratingInput.isNullOrEmpty()
    ) {
        Toast.makeText(
            context, context.resources.getString(R.string.movie_all_fields_compulsory_warning),
            Toast.LENGTH_SHORT
        ).show()
        return null
    }
    Toast.makeText(
        context, context.resources.getString(R.string.product_success),
        Toast.LENGTH_SHORT
    ).show()
    return Movie(
        name = nameInput,
        description = descriptionInput,
        price = priceInput.toInt(),
        color = colorInput,
        condition = conditionInput,
        category = categoryInput,
        rating = ratingInput.toDouble(),
    )
}