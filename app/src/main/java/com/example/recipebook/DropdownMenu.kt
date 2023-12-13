package com.example.recipebook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.recipebook.data.ItemCategory

//@Composable
//fun dropDownMenu(categoryName : String , subcategories: List<ItemCategory>) {
//
//    var expanded by remember { mutableStateOf(false) }
//  //  val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
//    var selectedText by remember { mutableStateOf("") }
//
//    var textfieldSize by remember { mutableStateOf(Size.Zero)}
//
//    val icon = if (expanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
//
//
//    Column(Modifier.padding(20.dp)) {
//        OutlinedTextField(
//            value = selectedText,
//            onValueChange = { selectedText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    //This value is used to assign to the DropDown the same width
//                    textfieldSize = coordinates.size.toSize()
//                },
//            label = {Text(categoryName)},
//            trailingIcon = {
//                Icon(icon,"contentDescription",
//                    Modifier.clickable { expanded = !expanded })
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
//        ) {
//            subcategories.forEach { label ->
//                DropdownMenuItem(onClick = {
//                    selectedText = label.title
//                    expanded = false
//                }) {
//                    Text(text = label.title)
//                }
//            }
//        }
//    }
//
//}

@Composable
fun dropDownMenu(
    categoryName: String,
    subcategories: List<ItemCategory>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .width(200.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text(categoryName) },
            trailingIcon = {
                Icon(
                    icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
//                .width(200.dp)
        ) {
            subcategories.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label.title
                    expanded = false
                    onItemSelected(label.title) // Trigger the callback with the selected value
                }) {
                    Text(text = label.title)
                }
            }
        }
    }
}

