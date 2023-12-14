package com.example.recipebook

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.recipebook.firebaselogic.CategoryInitializer
import com.example.recipebook.navigation.AuthNavGraph
import com.example.recipebook.presentation.GoogleSignInManager
import com.example.recipebook.ui.theme.RecipeBookTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val categoryInitializer = CategoryInitializer()
    private var googleSignInManager: GoogleSignInManager?=null
    private var signedIn:Boolean = false
    override fun onStart() {
        super.onStart()
        if(googleSignInManager!!.isUserAlreadySignIn){
            Toast.makeText(this, "Already Signed in.", Toast.LENGTH_SHORT).show()
            signedIn = true
            Log.d("TAG", "sign in status after user checking $signedIn")

        }
        else{
            signedIn = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
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

//                    AuthNavGraph()
                    AuthNavGraph(googleSignInManager, signedIn)
                    // Initialize categories when the app starts
                    categoryInitializer.initializeCategories()

//                    MainScreen()
                }

            }
        }
    }
    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == googleSignInManager !!.GOOGLE_SIGN_IN){
////            var navController: NavHostController
//            googleSignInManager !!.handleSignInResult(data, navController = NavC){ signedIn ->
//                // Handle the signedIn status here
//                if (signedIn) {
////                    Toast.makeText(this, "Sign In Finished Successfully", Toast.LENGTH_SHORT).show()
////
//                } else {
////                    Toast.makeText(context, "Sign In Finished Unsuccessfully", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
}



class AdViewState {
    var adView: AdView? = null
}

@Composable
fun AdViewContainer() {
    val adViewState = remember { AdViewState() }

    if (adViewState.adView == null) {
        adViewState.adView = createAdView()
    }

    // Load the ad
    adViewState.adView?.loadAd(AdRequest.Builder().build())

    AndroidView(
        factory = { context ->
            RelativeLayout(context).apply {
                addView(adViewState.adView)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun createAdView(): AdView {
    val context = LocalContext.current
    var adView = remember { AdView(context) }

    DisposableEffect(LocalContext.current) {
        adView.apply {
//            adSize = AdSize.BANNER
            adUnitId = "KitchenAd"
            loadAd(AdRequest.Builder().build())
        }
        onDispose {
            // Clean up when the composable is disposed, if needed.
            adView.destroy()
        }
    }

    return adView
}
