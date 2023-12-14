package com.example.recipebook.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.recipebook.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input field for email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if(email.isEmpty()){
                    Log.d("TAG","Please enter email address")
                    Toast.makeText(context, "Please enter email address", Toast.LENGTH_SHORT).show()
                }else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener{
                                task->
                            if(task.isSuccessful){
                                Log.d("TAG","Email sent successfully")
                                Toast.makeText(context, "Email sent successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screens.SignInScreen.route)
                            }
                            else{
                                Log.d("TAG","Email couldn't be sent")
                                Toast.makeText(context, "Email couldn't be sent", Toast.LENGTH_SHORT).show()
                            }
                        }


                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}