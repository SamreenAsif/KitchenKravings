package com.example.recipebook

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import coil.compose.rememberImagePainter

data class Recipe(
    val title: String,
    val coverImageUri: String,
    val description: String,
    val cookingTime: String,
    val servings: String,
    val videoUri: String,
    val ingredients: List<String>,
    val directions: List<String>,
    val category: String
)

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddRecipeScreen(onRecipeAdded: (Recipe) -> Unit) {
//    var title by remember { mutableStateOf("") }
//    var coverImageUri by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var cookingTime by remember { mutableStateOf("") }
//    var servings by remember { mutableStateOf("") }
//    var videoUri by remember { mutableStateOf("") }
//    var ingredientText by remember { mutableStateOf("") }
//    var directionText by remember { mutableStateOf("") }
//    var category by remember { mutableStateOf("") }
//
//    var ingredients by remember { mutableStateOf(emptyList<String>()) }
//    var directions by remember { mutableStateOf(emptyList<String>()) }
//    val YellowishOrange = Color(0xFFFFA500)
//    var showVideoError by remember { mutableStateOf(false) }
//    var showIngredientsError by remember { mutableStateOf(false) }
//    var showDirectionsError by remember { mutableStateOf(false) }
//    var isDialogVisible by remember { mutableStateOf(false) }
//    var selectedCategory by remember { mutableStateOf("") }
//    val categories = listOf("Asian", "Chinese", "Continental")
//    var isVideoAdded by remember { mutableStateOf(false) }
//    var isIngredientsAdded by remember { mutableStateOf(false) }
//    var isDirectionsAdded by remember { mutableStateOf(false) }
//
//    val context = LocalContext.current
//    val activity = LocalContext.current as? ComponentActivity
//
//    // ActivityResultLauncher for video picker
//    val videoPickerLauncher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let {
//                videoUri = it.toString()
//                isVideoAdded = true
//            }
//        }
//
//    Scaffold(
//        topBar = {
//            // Wrap TopAppBar in a Surface and set the background color
//            TopAppBar(
//                title = { Text("Add Recipe") },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            // Handle back button click
//                        }
//                    ) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            if (isVideoAdded && isIngredientsAdded && isDirectionsAdded) {
//                                val recipe = Recipe(
//                                    title = title,
//                                    coverImageUri = coverImageUri,
//                                    description = description,
//                                    cookingTime = cookingTime,
//                                    servings = servings,
//                                    videoUri = videoUri,
//                                    ingredients = ingredients,
//                                    directions = directions,
//                                    category = category
//                                )
//                                onRecipeAdded(recipe)
//                                isDialogVisible = true
//                            } else {
//                                showVideoError = !isVideoAdded
//                                showIngredientsError = !isIngredientsAdded
//                                showDirectionsError = !isDirectionsAdded
//                            }
//                        }
//                    ) {
//                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Recipe")
//                    }
//                },
//
//            )
//
//        },
//
//        content = {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                contentPadding = PaddingValues(top=30.dp , bottom=50.dp)
//            ) {
//                // Title Section
//                item {
//                    TextField(
//                        value = title,
//                        onValueChange = { title = it },
//                        label = { Text("Recipe Title") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Cover Image Section
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                            .border(1.dp, Color.Gray)
//                            .background(Color.LightGray)
//                            .clickable {
//                                // Open image picker
//                                // Update the following line according to your image picker implementation
//                                // coverImagePickerLauncher.launch("image/*")
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        // Display selected image or an icon to add an image
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Description Section
//                item {
//                    TextField(
//                        value = description,
//                        onValueChange = { description = it },
//                        label = { Text("Description") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Cooking Time Section
//                item {
//                    TextField(
//                        value = cookingTime,
//                        onValueChange = { cookingTime = it },
//                        label = { Text("Cooking Time") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Servings Section
//                item {
//                    TextField(
//                        value = servings,
//                        onValueChange = { servings = it },
//                        label = { Text("Servings") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 8.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Video Section
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                            .border(1.dp, Color.Gray)
//                            .background(Color.LightGray)
//                            .clickable {
//                                // Open video picker
//                                videoPickerLauncher.launch("video/*")
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        if (isVideoAdded) {
//                            Icon(
//                                imageVector = Icons.Default.Check,
//                                contentDescription = "Video Added",
//                                tint = Color.Green,
//                                modifier = Modifier.size(50.dp)
//                            )
//                        } else {
//                            Icon(
//                                imageVector = Icons.Default.PlayArrow,
//                                contentDescription = "Add Video",
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Category Section (Checkbox)
//                item {
//                    Text(
//                        text = "Category",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp
//                    )
//                    Column {
//                        categories.forEach { categoryOption ->
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(top = 8.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Checkbox(
//                                    checked = selectedCategory == categoryOption,
//                                    onCheckedChange = {
//                                        selectedCategory = if (it) categoryOption else ""
//                                    },
//                                    modifier = Modifier
//                                        .padding(end = 8.dp)
//                                )
//                                Text(text = categoryOption)
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Ingredients Section
//                item {
//                    Text(
//                        text = "Ingredients",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp
//                    )
//
//                    Column{
//                        ingredients.forEach { ingredient ->
//                            Text(text = "• $ingredient")
//                        }
//                    }
//                    AddItemSection(
//                        itemText = ingredientText,
//                        onItemTextChanged = { ingredientText = it },
//                        onAddItem = {
//                            if (ingredientText.isNotBlank()) {
//                                ingredients = ingredients + listOf(ingredientText)
//                                ingredientText = ""
//                                isIngredientsAdded = true
//                            }
//                        },
//                        showError = showIngredientsError
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Directions Section
//                item {
//                    Text(
//                        text = "Directions",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp
//                    )
//                    Column{
//                        directions.forEach { direction ->
//                            Text(text = "• $direction")
//                        }
//                    }
//                    AddItemSection(
//                        itemText = directionText,
//                        onItemTextChanged = { directionText = it },
//                        onAddItem = {
//                            if (directionText.isNotBlank()) {
//                                directions = directions + listOf(directionText)
//                                directionText = ""
//                                isDirectionsAdded = true
//                            }
//                        },
//                        showError = showDirectionsError
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Add Recipe Button
//                item {
//                    Button(
//                        onClick = {
//                            if (isVideoAdded && isIngredientsAdded && isDirectionsAdded) {
//                                val recipe = Recipe(
//                                    title = title,
//                                    coverImageUri = coverImageUri,
//                                    description = description,
//                                    cookingTime = cookingTime,
//                                    servings = servings,
//                                    videoUri = videoUri,
//                                    ingredients = ingredients,
//                                    directions = directions,
//                                    category = category
//                                )
//                                onRecipeAdded(recipe)
//                                isDialogVisible = true
//                            } else {
//                                showVideoError = !isVideoAdded
//                                showIngredientsError = !isIngredientsAdded
//                                showDirectionsError = !isDirectionsAdded
//                            }
//                        },
//                        modifier = Modifier
//                            .width(130.dp)
//
//                    ) {
//                        Text("Add Recipe")
//                    }
//                }
//            }
//
//        }
//    )
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(onRecipeAdded: (Recipe) -> Unit) {
    var title by remember { mutableStateOf("") }
    var coverImageUri by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf("") }
    var videoUri by remember { mutableStateOf("") }
    var ingredientText by remember { mutableStateOf("") }
    var directionText by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var ingredients by remember { mutableStateOf(emptyList<String>()) }
    var directions by remember { mutableStateOf(emptyList<String>()) }
    val YellowishOrange = Color(0xFFFFA500)
    var showVideoError by remember { mutableStateOf(false) }
    var showIngredientsError by remember { mutableStateOf(false) }
    var showDirectionsError by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf("Asian", "Chinese", "Continental")
    var isVideoAdded by remember { mutableStateOf(false) }
    var isIngredientsAdded by remember { mutableStateOf(false) }
    var isDirectionsAdded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity

    // ActivityResultLauncher for video picker
    val videoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                videoUri = it.toString()
                isVideoAdded = true
            }
        }
    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            // It's an image
            coverImageUri = it.toString()
        }
    }


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
                            if (isVideoAdded && isIngredientsAdded && isDirectionsAdded) {
                                val recipe = Recipe(
                                    title = title,
                                    coverImageUri = coverImageUri,
                                    description = description,
                                    cookingTime = cookingTime,
                                    servings = servings,
                                    videoUri = videoUri,
                                    ingredients = ingredients,
                                    directions = directions,
                                    category = category
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(top = 30.dp, bottom = 50.dp)
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
                                // Open image picker
                                imagePickerLauncher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (coverImageUri.isNotBlank()) {
                            Image(
                                painter = rememberImagePainter(data = coverImageUri),
                                contentDescription = "Cover Image" ,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.medium)
                            )
                        } else {
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
                item {
                    TextField(
                        value = cookingTime,
                        onValueChange = { cookingTime = it },
                        label = { Text("Cooking Time") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // Servings Section
                item {
                    TextField(
                        value = servings,
                        onValueChange = { servings = it },
                        label = { Text("Servings") },
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
                                videoPickerLauncher.launch("video/*")
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
                item {
                    Text(
                        text = "Category",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        categories.forEach { categoryOption ->
                            Checkbox(
                                checked = selectedCategory == categoryOption,
                                onCheckedChange = {
                                    selectedCategory = if (it) categoryOption else ""
                                },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                            Text(text = categoryOption, modifier = Modifier.padding(start = 4.dp))
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
                            if (isVideoAdded && isIngredientsAdded && isDirectionsAdded) {
                                val recipe = Recipe(
                                    title = title,
                                    coverImageUri = coverImageUri,
                                    description = description,
                                    cookingTime = cookingTime,
                                    servings = servings,
                                    videoUri = videoUri,
                                    ingredients = ingredients,
                                    directions = directions,
                                    category = selectedCategory
                                )
                                onRecipeAdded(recipe)
                                isDialogVisible = true
                            } else {
                                showVideoError = !isVideoAdded
                                showIngredientsError = !isIngredientsAdded
                                showDirectionsError = !isDirectionsAdded
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
//@Composable
//fun VideoPlayer(videoUri: String) {
//    val context = LocalContext.current
//    val videoView = remember { VideoView(context) }
//
//    DisposableEffect(videoUri) {
//        // Load video when the URI changes
//        videoView.setVideoURI(Uri.parse(videoUri))
//        onDispose {
//            // Release resources when the composable is disposed
//            videoView.stopPlayback()
//        }
//    }
//
//    AndroidView(
//        factory = { videoView },
//        modifier = Modifier
//            .fillMaxSize()
//            .clipToBounds()
//            .background(Color.Black) // Set a background color for the video view
//    )
//}

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

