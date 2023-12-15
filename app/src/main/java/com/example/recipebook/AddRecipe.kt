package com.example.recipebook

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipebook.data.Recipe
import com.example.recipebook.util.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
val cookingTime = listOf("10" , "15" , "20","30","45","60")


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(onRecipeAdded: (Recipe) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf("") }
    var ingredientText by remember { mutableStateOf("") }
    var directionText by remember { mutableStateOf("") }
//    var category by remember { mutableStateOf(emptyList<String>()) }
    var ingredients by remember { mutableStateOf(emptyList<String>()) }
    var directions by remember { mutableStateOf(emptyList<String>()) }
    var showVideoError by remember { mutableStateOf(false) }
    var showIngredientsError by remember { mutableStateOf(false) }
    var showDirectionsError by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(emptyList<String>()) }
    var isVideoAdded by remember { mutableStateOf(false) }
    var isImageAdded by remember { mutableStateOf(false) }
    var isIngredientsAdded by remember { mutableStateOf(false) }
    var isDirectionsAdded by remember { mutableStateOf(false) }
    var typeState by remember { mutableStateOf("") }
    var drinkState by remember { mutableStateOf("") }
    var cuisineState by remember { mutableStateOf("") }

    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Get firebase realtime database instance
    val recipesRef = FirebaseDatabase.getInstance().getReference("recipes")

    val coroutineScope = rememberCoroutineScope()

    // For Image
    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
            isImageAdded = true
        }
    )

    //For Video picker
    var videouri by remember{
        mutableStateOf<Uri?>(null)
    }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            videouri = it
            isVideoAdded = true
        }
    )

    // Create a SnackbarHostState
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Recipe") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Handle back button click
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isIngredientsAdded && isDirectionsAdded) {
                                val recipe = Recipe(
                                    chefId = userId,
                                    title = title,
                                    coverImageUri = uri?.toString(),
                                    description = description,
                                    cookingTime = cookingTime,
                                    servings = servings,
                                    videoUri =  videouri?.toString(),
                                    ingredients = ingredients,
                                    directions = directions,
                                    type = typeState,
                                    cuisine = cuisineState,
                                    drinkType = drinkState

                                )
                                onRecipeAdded(recipe)
                            } else {
                                // Show error message or take appropriate action
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Recipe")
                    }
                },
            )
        },
        content = {
            // Add SnackbarHost to show Snackbars
            SnackbarHost(
                hostState = snackbarHostState,
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(top = 30.dp, bottom = 200.dp)
            ) {
                // Title Section
                item {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Recipe Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // Cover Image Section
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(1.dp, Color.Gray)
                            .background(Color.LightGray)
                            .clickable {
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                                if (uri == null) {
                                    val uriString = uri?.toString() ?: "URI is null"
                                    Toast
                                        .makeText(
                                            context,
                                            "Selected URI: $uriString",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                    Log.e(
                                        "AddRecipeScreen",
                                        "Error: Unable to process URI after img added - $uriString"
                                    )
                                }
                            },
                        contentAlignment = Alignment.Center
                    )
                    {

                        if (isImageAdded) {
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier.size(248.dp)
                            )
                        }
                        else
                        {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Image",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Description Section
                item {
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // Cooking Time Section
//                item {
//                    TextField(
//                        value = cookingTime,
//                        onValueChange = { cookingTime = it },
//                        label = { Text("Cooking Time") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 8.dp)
//                    )
//                }
                item{
                    ChipTitle(title = "Cooking Time")
                    test(com.example.recipebook.cookingTime) { selectedValue ->
                        cookingTime = selectedValue
                    }
                }

                // Servings Section
                item {
                    TextField(
                        value = servings,
                        onValueChange = { servings = it },
                        label = { Text("Servings") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // Video Section
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(1.dp, Color.Gray)
                            .background(Color.LightGray)
                            .clickable {
                                // Open video picker
                                videoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                                )
                                if (videouri == null) {
                                    val uriString = videouri?.toString() ?: "URI is null"
                                    Toast
                                        .makeText(
                                            context,
                                            "Selected URI: $uriString",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                    Log.e(
                                        "Vide0Error",
                                        "Error: Unable to process URI after video added - $uriString"
                                    )
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isVideoAdded) {

                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Video Added",
                                tint = Color.Green,
                                modifier = Modifier.size(50.dp)
                            )
                        }

                        else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Add Video",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Category Section (Checkbox)
//                item {
//                    Text(
//                        text = "Category",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 8.dp)
//                    ) {
//                        categories.forEach { categoryOption ->
//                            Checkbox(
//                                checked = selectedCategory == categoryOption,
//                                onCheckedChange = {
//                                    selectedCategory = if (it) categoryOption else ""
//                                },
//                                modifier = Modifier
//                                    .padding(end = 8.dp)
//                            )
//                            Text(text = categoryOption, modifier = Modifier.padding(start = 4.dp))
//                        }
//                    }
//                }
//
//                item{
//                   selectedCategory = ChipDisplay()
//                }
                item {
                    Column() {

                        // Combine values into a List<String>
                        ChipTitle(title = "Type")
                        test(type) { selectedValue ->
                            typeState = selectedValue
                        }
                        // Check if the selected type is "Beverage"
                        if (typeState == "Beverages") {
                            // Display "Drink Type" options
                            ChipTitle(title = "Drink Type")
                            test(drinkType) { drinkTypeValue ->
                                drinkState = drinkTypeValue
                            }
                        }

                        ChipTitle(title = "Cuisine")
                        test(cuisineTitles) { selectedValue ->
                            cuisineState = selectedValue
                        }
                    }
                }
                // Ingredients Section
                item {
                    Text(
                        text = "Ingredients",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column {
                        ingredients.forEach { ingredient ->
                            Text(text = "• $ingredient")
                        }
                    }
                    AddItemSection(
                        itemText = ingredientText,
                        onItemTextChanged = { ingredientText = it },
                        onAddItem = {
                            if (ingredientText.isNotBlank()) {
                                ingredients = ingredients + listOf(ingredientText)
                                ingredientText = ""
                                isIngredientsAdded = true
                            }
                        },
                        showError = showIngredientsError
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Directions Section
                item {
                    Text(
                        text = "Directions",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Column {
                        directions.forEach { direction ->
                            Text(text = "• $direction")
                        }
                    }
                    AddItemSection(
                        itemText = directionText,
                        onItemTextChanged = { directionText = it },
                        onAddItem = {
                            if (directionText.isNotBlank()) {
                                directions = directions + listOf(directionText)
                                directionText = ""
                                isDirectionsAdded = true
                            }
                        },
                        showError = showDirectionsError
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Add Recipe Button
                item {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                // Check if image, video, ingredients, and directions are added
                                if (isImageAdded && isVideoAdded && isIngredientsAdded && isDirectionsAdded) {
                                    // Convert the content URI to Firebase Storage URLs
                                    uri?.let { imageUri ->
                                        val imageUrl = StorageUtil.uploadToStorage(imageUri, context, "image")

                                        videouri?.let { videoUri ->
                                            val videoUrl = StorageUtil.uploadToStorage(videoUri, context, "video")

                                            // Check if the image and video uploads were successful
                                            if (imageUrl.isNotEmpty() && videoUrl.isNotEmpty()) {
                                                // Create the Recipe object with the Firebase Storage URLs
                                                val recipe = Recipe(
                                                    chefId = userId,
                                                    title = title,
                                                    coverImageUri = imageUrl,
                                                    description = description,
                                                    cookingTime = cookingTime,
                                                    servings = servings,
                                                    videoUri = videoUrl,
                                                    ingredients = ingredients,
                                                    directions = directions,
                                                    type = typeState,
                                                    cuisine = cuisineState,
                                                    drinkType = drinkState
                                                )

                                            //     Push the recipe data to the "recipes" node asynchronously
                                            withContext(Dispatchers.IO) {
                                                try {
                                                    val newRecipeRef = recipesRef.push()
                                                    val recipeId = newRecipeRef.key ?: ""
                                                    recipe.id = recipeId
                                                    newRecipeRef.setValue(recipe)
                                                        .addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                Toast.makeText(context , " INside success" , Toast.LENGTH_LONG)
//                                                                // Show a Snackbar to notify the user
//                                                                coroutineScope.launch {
//                                                                    // Show a Snackbar to notify the user
//                                                                    snackbarHostState.showSnackbar("Recipe added successfully")
//                                                                }
//                                                                Log.d("Snackbar" , "Snackbar inside")

                                                            } else {
                                                                // Handle failure
                                                                val exception = task.exception
                                                                Log.e("FirebaseException", "Error setting value in the database", exception)
                                                            }
                                                        }
                                                } catch (e: Exception) {
                                                    // Handle exceptions if needed
                                                    // For example, you can log the error or show an error message to the user
                                                }
                                            }

                                                // Notify the UI that the recipe has been added
                                                onRecipeAdded(recipe)
                                                isDialogVisible = true
                                            } else {
                                                // Show an error message or handle the case when image or video upload fails
                                                Toast.makeText(context, "Image or video upload failed", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                } else {
                                    // Show error messages or take appropriate action
                                    showVideoError = !isVideoAdded
                                    showIngredientsError = !isIngredientsAdded
                                    showDirectionsError = !isDirectionsAdded
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Add Recipe")
                    }
                }

            }

        }
    )
}


@Composable
fun showRecipeSavedAlert(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = { Text("Recipe Saved Successfully") },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemSection(
    itemText: String,
    onItemTextChanged: (String) -> Unit,
    onAddItem: () -> Unit,
    showError: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = itemText,
            onValueChange = { onItemTextChanged(it) },
            label = { Text("Add Item") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onAddItem,
            enabled = itemText.isNotBlank()
        ) {
            Text("Add")
        }
    }

    // Error message for incomplete sections
    if (showError) {
        Text(
            text = "Please complete this section before adding the recipe.",
            color = Color.Red,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
    }
}
