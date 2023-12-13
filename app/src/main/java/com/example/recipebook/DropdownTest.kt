package com.example.recipebook

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.recipebook.Lists.Beverages
import com.example.recipebook.Lists.CuisineList
import com.example.recipebook.Lists.Desserts
import com.example.recipebook.Lists.MealTypes
import com.example.recipebook.Lists.coursesList

//@Composable
//fun testDropdown(){
//    Column(){
//        dropDownMenu("Course" , coursesList)
//        dropDownMenu("Cuisine" , CuisineList)
//        dropDownMenu("Beverage" , Beverages)
//        dropDownMenu("Dessert" , Desserts)
//        dropDownMenu("Meal Type" , MealTypes)
//        dropDownMenu("Protein" , Proteins)
//        dropDownMenu("Dietary Preferences" ,Diet)
//    }
//}
//@Composable
//fun testDropdown() {
//    var selectedValues = remember { mutableStateListOf<String>() }
//
//    fun onItemSelected(label: String, value: String) {
//        // Update or add the selected value to the array
//        val index = selectedValues.indexOfFirst { it.startsWith(label) }
//        if (index != -1) {
//            selectedValues[index] = "$label: $value"
//        } else {
//            selectedValues.add("$label: $value")
//        }
//    }
//
//    Column() {
//        dropDownMenu("Course", coursesList) { value -> onItemSelected("Course", value) }
//        dropDownMenu("Cuisine", CuisineList) { value -> onItemSelected("Cuisine", value) }
//        dropDownMenu("Beverage", Beverages) { value -> onItemSelected("Beverage", value) }
//        dropDownMenu("Dessert", Desserts) { value -> onItemSelected("Dessert", value) }
//        dropDownMenu("Meal Type", MealTypes) { value -> onItemSelected("Meal Type", value) }
//        dropDownMenu("Protein", Proteins) { value -> onItemSelected("Protein", value) }
//        dropDownMenu("Dietary Preferences", Diet) { value -> onItemSelected("Diet", value) }
//
//        // Use selectedValues array as needed
//    }
//}


@Composable
fun testDropdown() {
    var selectedValues = remember { mutableStateListOf<String>() }

    fun onItemSelected(label: String, value: String) {
        // Update or add the selected value to the array
        val index = selectedValues.indexOfFirst { it.startsWith(label) }
        if (index != -1) {
            selectedValues[index] = "$label: $value"
        } else {
            selectedValues.add("$label: $value")
        }
    }

    var showToast by remember { mutableStateOf(false) }

    Column() {
        dropDownMenu("Course", coursesList) { value -> onItemSelected("Course", value) }
        dropDownMenu("Cuisine", CuisineList) { value -> onItemSelected("Cuisine", value) }
        dropDownMenu("Beverage", Beverages) { value -> onItemSelected("Beverage", value) }
        dropDownMenu("Dessert", Desserts) { value -> onItemSelected("Dessert", value) }
        dropDownMenu("Meal Type", MealTypes) { value -> onItemSelected("Meal Type", value) }
//        dropDownMenu("Protein", Proteins) { value -> onItemSelected("Protein", value) }
//        dropDownMenu("Dietary Preferences", Diet) { value -> onItemSelected("Diet", value) }

        // Button to display toast
        Button(
            onClick = { showToast = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Show Toast")
        }

        // Use selectedValues array as needed

        // Display toast when showToast is true
        if (showToast) {
            showToast(selectedValues.joinToString("\n") , LocalContext.current)
            Log.d("Toast" ,selectedValues.joinToString("\n"))
            showToast = false
        }
    }
}

// Function to show a toast messages
fun showToast(message: String , context : Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
