package com.example.recipebook



import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebook.Lists.CuisineList

val type = listOf("Breakfast" , "lunch" , "Snacks" , "Dinner" , "Soups" , "Salads","Pastries","Beverages")
val drinkType = listOf("Tea" , "Coffee" , "Juice" , "Smoothie")
val cuisineTitles: List<String> = CuisineList.map { it.title }



@Composable
fun ChipDisplay() : List<String> {
    var keyword: List<String> = listOf(type[0] , drinkType[0], cuisineTitles[0])
    Column() {
        var typeState by remember { mutableStateOf("") }
        var drinkState by remember { mutableStateOf("") }
        var cuisineState by remember { mutableStateOf("") }

        // Combine values into a List<String>
        keyword  = listOf(typeState, drinkState, cuisineState)
            .filter { it.isNotBlank() && it != "Beverage" }


        ChipTitle(title = "Type")
        test(type) { selectedValue ->
            typeState = selectedValue
        }

        // Check if the selected type is "Beverage"
        if (typeState == "Beverage") {
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
    return keyword
}

@Composable
fun ChipTitle(title : String){
    Text(text= title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun test(itemsList : List<String>, onItemSelected: (String) -> Unit) {
    val highlightColor: Color = Color(0xFFF78c3b)

    val contextForToast = LocalContext.current.applicationContext

    var selectedItem by remember {
        mutableStateOf("") // initially, first item is selected
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(itemsList) { item ->
            FilterChip(
                modifier = Modifier.padding(all = 6.dp), // gap between items
                selected = (item == selectedItem),
                onClick = {
                    selectedItem = item
                    onItemSelected(item) // Invoke the callback with the selected value
                    Toast.makeText(contextForToast, selectedItem, Toast.LENGTH_SHORT).show()
                },
                label = {
                    Text(
                        text = item,
                        fontSize = 20.sp,
                        fontWeight = if (item == selectedItem) FontWeight.Medium else FontWeight.Normal
                    )
                },
                leadingIcon = if (item == selectedItem) {
                    {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier.size(
                                FilterChipDefaults.IconSize
                            )
                        )
                    }
                } else {
                    null
                },
                colors = FilterChipDefaults.filterChipColors(
                    labelColor = highlightColor,
                    selectedLabelColor = highlightColor,
                    selectedLeadingIconColor = highlightColor,
                    selectedContainerColor = highlightColor.copy(alpha = 0.1f)
                ),
                border = FilterChipDefaults.filterChipBorder(
                    selectedBorderColor = highlightColor,
                    selectedBorderWidth = 2.dp,
                    borderColor = highlightColor
                )
            )
        }
    }
}
