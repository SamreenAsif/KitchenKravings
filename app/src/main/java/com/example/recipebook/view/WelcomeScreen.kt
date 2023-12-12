package com.example.recipebook.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipebook.R
import com.example.recipebook.navigation.Screens

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    var showBranding by rememberSaveable { mutableStateOf(true) }

    val backgroundImage = painterResource(id = R.drawable.homepage)


    Scaffold(modifier = Modifier) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(id= R.drawable.homepage),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Background Image",
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 600.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                AnimatedVisibility(
                    showBranding,
                    Modifier.fillMaxWidth()
                ) {
                    //Branding()
                }

                ElevatedButton(onClick = {
                    navController.navigate(Screens.MainScreen.route)
                },
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(56.dp) // Adjust the height as needed
                        .padding(horizontal = 24.dp, vertical = 8.dp), // Adjust the padding as needed
                    shape = RoundedCornerShape(8.dp), // Adjust the corner radius as needed
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // Adjust the background color as needed
                        contentColor = Color.White // Adjust the text color as needed
                    )) {
                    Text("Get Started",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            letterSpacing = 0.5.sp
                        ),
                        modifier = Modifier.padding(10.dp)
                    )

                }
            }
        }
    }
}

@Composable
private fun Branding(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Logo(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 76.dp)
        )
        Text(
            text = "Kitchen Kravings",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )
    }
}
@Composable
private fun Logo(
    modifier: Modifier = Modifier,

) {
    val assetId = R.drawable.logo
    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )
}

