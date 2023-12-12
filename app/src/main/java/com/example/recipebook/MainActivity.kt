package com.example.recipebook

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.recipebook.firebaselogic.CategoryInitializer
import com.example.recipebook.navigation.AuthNavGraph
import com.example.recipebook.presentation.GoogleSignInManager
import com.example.recipebook.ui.theme.RecipeBookTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val categoryInitializer = CategoryInitializer()
    private var googleSignInManager:GoogleSignInManager?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        googleSignInManager = GoogleSignInManager.getInstance(this)
        googleSignInManager?.setupGoogleSignInOptions()
        setContent {
            RecipeBookTheme {
                FirebaseApp.initializeApp(LocalContext.current)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White),
                    color = Color.White,

                ) {

                    AuthNavGraph(googleSignInManager)
                    // Initialize categories when the app starts
                    categoryInitializer.initializeCategories()

//                    MainScreen()
                }

            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(googleSignInManager!!.isUserAlreadySignIn){
//            Toast.makeText(this, "Already Signed in.", Toast.LENGTH_SHORT).show()
//        }
//        else{}
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == googleSignInManager !!.GOOGLE_SIGN_IN){
            googleSignInManager !!.handleSignInResult(data)
        }
    }
}
