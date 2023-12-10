//package com.example.recipebook
//
//import android.annotation.SuppressLint
//import android.net.Uri
//import android.widget.VideoView
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.ui.graphics.Color
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material3.*
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.clipToBounds
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.net.toUri
//import coil.compose.rememberImagePainter
//import com.example.recipebook.util.StorageUtil
//import com.google.firebase.database.FirebaseDatabase
//
//import kotlinx.coroutines.launch
//import androidx.lifecycle.lifecycleScope
//import com.google.firebase.ktx.Firebase
//
////
////data class Recipe(
////    val title: String,
////    val coverImageUri: String,
////    val description: String,
////    val cookingTime: String,
////    val servings: String,
////    val videoUri: String,
////    val ingredients: List<String>,
////    val directions: List<String>,
////    val category: String
////)
//
//import java.io.Serializable
//
//data class Recipe @JvmOverloads constructor(
//    val title: String? = null,
//    val coverImageUri: Uri? = null,
//    val description: String? = null,
//    val cookingTime: String? = null,
//    val servings: String? = null,
////    val videoUri: Uri? = null,
//    val ingredients: List<String>? = null,
//    val directions: List<String>? = null,
//    val category: String? = null
//) : Serializable
//
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddRecipeScreen(onRecipeAdded: (Recipe) -> Unit) {
//    var title by remember { mutableStateOf("") }
//    var coverImageUri by remember { mutableStateOf<Uri?>(null) }  // Use Uri type here
//    var description by remember { mutableStateOf("") }
//    var cookingTime by remember { mutableStateOf("") }
//    var servings by remember { mutableStateOf("") }
////    var videoUri by remember { mutableStateOf<Uri?>(null) } // Use Uri type here
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
//    var isImageAdded by remember { mutableStateOf(false) }
//    var isIngredientsAdded by remember { mutableStateOf(false) }
//    var isDirectionsAdded by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    val activity = LocalContext.current as? ComponentActivity
//    val coroutineScope = rememberCoroutineScope()
//
//    // ActivityResultLauncher for video picker
//
//
//    val database = FirebaseDatabase.getInstance()
//    val recipesRef = database.getReference("recipes")
//
//    // For Image picker
////    var imageUri by remember{
////        mutableStateOf<Uri?>(null)
////    }
////    val imagePicker = rememberLauncherForActivityResult(
////        contract = ActivityResultContracts.PickVisualMedia(),
////        onResult = {
////            imageUri = it
////        }
////    )
////
////    var videouri by remember{
////        mutableStateOf<Uri?>(null)
////    }
////    val videoPicker = rememberLauncherForActivityResult(
////        contract = ActivityResultContracts.PickVisualMedia(),
////        onResult = {
////            videouri = it
////            isVideoAdded = true
////        }
////    )
//
//    var uri by remember{
//        mutableStateOf<Uri?>(null)
//    }
//
//    val singlePhotoPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = {
//            uri = it
//            isImageAdded = true
//        }
//    )
//    //For Video picker
//    var Videouri by remember{
//        mutableStateOf<Uri?>(null)
//    }
//
//    val VideoPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = {
//            Videouri = it
//            isVideoAdded = true
//        }
//    )
//
//    Scaffold(
//        topBar = {
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
//                            if ( isIngredientsAdded && isDirectionsAdded) {
//                                val recipe = Recipe(
//                                    title = title,
//                                    coverImageUri = uri,
//                                    description = description,
//                                    cookingTime = cookingTime,
//                                    servings = servings,
////                                    videoUri = Videouri,
//                                    ingredients = ingredients,
//                                    directions = directions,
//                                    category = category
//                                )
//                                onRecipeAdded(recipe)
//                            } else {
//                                // Show error message or take appropriate action
//                            }
//                        }
//                    ) {
//                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Recipe")
//                    }
//                },
//            )
//        },
//        content = {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                contentPadding = PaddingValues(top = 30.dp, bottom = 50.dp)
//            ) {
//                // Title Section
//                item {
//                    TextField(
//                        value = title,
//                        onValueChange = { title = it },
//                        label = { Text("Recipe Title") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 8.dp)
//                    )
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
//                                singlePhotoPicker.launch(
//                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                                )
//                            },
//                        contentAlignment = Alignment.Center
//                    )
//                    {
//                        if (isImageAdded) {
//
//                            Icon(
//                                imageVector = Icons.Default.Add,
//                                contentDescription = "Image Added",
//                                tint = Color.Green,
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//
//                        else {
//                            Icon(
//                                imageVector = Icons.Default.PlayArrow,
//                                contentDescription = "Add Image",
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//                    }
////                    {
////                        if (coverImageUri.isNotBlank()) {
////                            Image(
////                                painter = rememberImagePainter(data = coverImageUri),
////                                contentDescription = "Cover Image" ,
////                                modifier = Modifier
////                                    .fillMaxSize()
////                                    .clip(MaterialTheme.shapes.medium)
////                            )
////                        } else {
////                            Icon(
////                                imageVector = Icons.Default.Add,
////                                contentDescription = "Add Image",
////                                modifier = Modifier.size(50.dp)
////                            )
////                        }
////                    }
////                    {
////                        Icon(
////                            imageVector = Icons.Default.Add,
////                            contentDescription = "Add Image",
////                            modifier = Modifier.size(50.dp)
////                        )
////                    }
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
//                            .padding(bottom = 8.dp)
//                    )
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
//                            .padding(bottom = 8.dp)
//                    )
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
//                            .padding(bottom = 8.dp)
//                    )
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
////                                coroutineScope.launch {
////                                    VideoPicker.launch(
////                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
////                                    )
////                                }
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        if (isVideoAdded) {
//
//                            Icon(
//                                imageVector = Icons.Default.Check,
//                                contentDescription = "Video Added",
//                                tint = Color.Green,
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//
//                        else {
//                            Icon(
//                                imageVector = Icons.Default.PlayArrow,
//                                contentDescription = "Add Video",
//                                modifier = Modifier.size(50.dp)
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//
//                // Category Section (Checkbox)
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
//                // Ingredients Section
//                item {
//                    Text(
//                        text = "Ingredients",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//
//                    Column {
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
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    Column {
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
//
//                            if (isIngredientsAdded && isDirectionsAdded) {
//                                val recipe = Recipe(
//                                    title = title,
//                                    coverImageUri = uri,
//                                    description = description,
//                                    cookingTime = cookingTime,
//                                    servings = servings,
////                                    videoUri = Videouri,
//                                    ingredients = ingredients,
//                                    directions = directions,
//                                    category = category
//                                )
//                                // Push the recipe data to the "recipes" node
//                                val newRecipeRef = recipesRef.push()
//                                newRecipeRef.setValue(recipe)
//
//                                onRecipeAdded(recipe)
//                                isDialogVisible = true
//
//                                uri?.let{
//                                    StorageUtil.uploadToStorage(uri=it, context=context, type="image")
//                                }
////                                Videouri?.let{
////                                    StorageUtil.uploadToStorage(uri=it, context=context, type="video")
////                                }
//                            } else {
//                                showVideoError = !isVideoAdded
//                                showIngredientsError = !isIngredientsAdded
//                                showDirectionsError = !isDirectionsAdded
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                    ) {
//                        Text("Add Recipe")
//                    }
//                }
//            }
//        }
//    )
//}
//
//
//@Composable
//fun showRecipeSavedAlert(onDismiss: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = {
//            onDismiss()
//        },
//        title = { Text("Recipe Saved Successfully") },
//        confirmButton = {
//            Button(
//                onClick = {
//                    onDismiss()
//                }
//            ) {
//                Text("OK")
//            }
//        }
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddItemSection(
//    itemText: String,
//    onItemTextChanged: (String) -> Unit,
//    onAddItem: () -> Unit,
//    showError: Boolean
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        OutlinedTextField(
//            value = itemText,
//            onValueChange = { onItemTextChanged(it) },
//            label = { Text("Add Item") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Done
//            ),
//            modifier = Modifier.weight(1f)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Button(
//            onClick = onAddItem,
//            enabled = itemText.isNotBlank()
//        ) {
//            Text("Add")
//        }
//    }
//
//    // Error message for incomplete sections
//    if (showError) {
//        Text(
//            text = "Please complete this section before adding the recipe.",
//            color = Color.Red,
//            fontSize = 14.sp,
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .fillMaxWidth()
//        )
//    }
//}
//
//
//

package com.example.recipebook

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.recipebook.util.StorageUtil
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//
//data class Recipe(
//    val title: String,
//    val coverImageUri: String,
//    val description: String,
//    val cookingTime: String,
//    val servings: String,
//    val videoUri: String,
//    val ingredients: List<String>,
//    val directions: List<String>,
//    val category: String
//)

import java.io.Serializable
data class Recipe(
    val title: String? = null,
    val coverImageUri: String? = null, // Store URI as String
    val description: String? = null,
    val cookingTime: String? = null,
    val servings: String? = null,
    val ingredients: List<String>? = null,
    val directions: List<String>? = null,
    val category: String? = null
) : Serializable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(onRecipeAdded: (Recipe) -> Unit) {
    var title by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf("") }
//    var videoUri by remember { mutableStateOf<Uri?>(null) } // Use Uri type here
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
    var isImageAdded by remember { mutableStateOf(false) }
    var isIngredientsAdded by remember { mutableStateOf(false) }
    var isDirectionsAdded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity

    val database = FirebaseDatabase.getInstance()
    val recipesRef = database.getReference("recipes")
    val coroutineScope = rememberCoroutineScope()

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
                                    title = title,
                                    coverImageUri = uri?.toString(),
                                    description = description,
                                    cookingTime = cookingTime,
                                    servings = servings,
//                                    videoUri = videoUri,
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
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                                if (uri == null) {
                                    val uriString = uri?.toString() ?: "URI is null"
                                    Toast.makeText(context, "Selected URI: $uriString", Toast.LENGTH_SHORT).show()
                                    Log.e("AddRecipeScreen", "Error: Unable to process URI after img added - $uriString")
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
//                item {
//                    Button(
//                        onClick = {
//                            if (isIngredientsAdded && isDirectionsAdded) {
//                                val recipe = Recipe(
//                                    title = title,
//                                    coverImageUri = uri,
//                                    description = description,
//                                    cookingTime = cookingTime,
//                                    servings = servings,
////                                    videoUri = videoUri,
//                                    ingredients = ingredients,
//                                    directions = directions,
//                                    category = selectedCategory
//                                )
//                                // Push the recipe data to the "recipes" node
//                                val newRecipeRef = recipesRef.push()
//                                newRecipeRef.setValue(recipe)
//
//                                onRecipeAdded(recipe)
//                                isDialogVisible = true
//                                uri?.let{
//                                    StorageUtil.uploadToStorage(uri=it, context=context, type="image")
//                                }
//                            } else {
//                                showVideoError = !isVideoAdded
//                                showIngredientsError = !isIngredientsAdded
//                                showDirectionsError = !isDirectionsAdded
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                    ) {
//                        Text("Add Recipe")
//                    }
//                }
                item {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (uri == null) {
                                    val uriString = uri?.toString() ?: "URI is null"
                                    Toast.makeText(context, "Selected URI: $uriString", Toast.LENGTH_SHORT).show()
                                    Log.e("AddRecipeScreen", "Error: Unable to process URI - $uriString")
                                }
                                if (isIngredientsAdded && isDirectionsAdded) {
                                    val recipe = Recipe(
                                        title = title,
                                        coverImageUri = uri?.toString(),
                                        description = description,
                                        cookingTime = cookingTime,
                                        servings = servings,
                                        ingredients = ingredients,
                                        directions = directions,
                                        category = selectedCategory
                                    )

                                    try {
                                        // Push the recipe data to the "recipes" node asynchronously
//                                        withContext(Dispatchers.IO) {
//                                            recipesRef.push().setValue(recipe)
//                                        }
                                        withContext(Dispatchers.IO) {
                                            recipesRef.push().setValue(recipe)
                                                .addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        // Handle success
                                                    } else {
                                                        // Handle failure
                                                        val exception = task.exception
                                                        Log.e("FirebaseException", "Error setting value in the database", exception)
                                                    }
                                                }
                                        }

                                        // Notify the UI that the recipe has been added
                                        onRecipeAdded(recipe)
                                        isDialogVisible = true

                                        // Upload the image to storage asynchronously
                                        uri?.let {
                                            withContext(Dispatchers.IO) {
                                                StorageUtil.uploadToStorage(uri = it, context = context, type = "image")
                                            }
                                        }
                                    } catch (e: Exception) {
                                        // Handle exceptions if needed
                                        // For example, you can log the error or show an error message to the user
                                    }
                                } else {
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
