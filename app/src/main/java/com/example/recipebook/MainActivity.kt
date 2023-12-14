package com.example.recipebook

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import com.example.recipebook.firebaselogic.CategoryInitializer
import com.example.recipebook.navigation.AuthNavGraph
import com.example.recipebook.presentation.GoogleSignInManager
import com.example.recipebook.ui.theme.RecipeBookTheme
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.atomic.AtomicBoolean



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val categoryInitializer = CategoryInitializer()
    private var googleSignInManager: GoogleSignInManager?=null
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private lateinit var adView: AdView
    private var adLoaded = false
    // Create a LiveData to observe sign-in results
    private val signInResultLiveData = MutableLiveData<Boolean>()
    override fun onStart() {
        super.onStart()
        if(googleSignInManager!!.isUserAlreadySignIn){
            Toast.makeText(this, "Already Signed in.", Toast.LENGTH_SHORT).show()
        }
        else{
        }
    }

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
                    modifier = Modifier
                        //.fillMaxSize()
                        .background(Color.White),
                    color = Color.White,

                ) {

//                    AuthNavGraph(googleSignInManager, signedIn)
                    // Initialize categories when the app starts
                    categoryInitializer.initializeCategories()

//                    MainScreen()
                    adView = AdView(this)
                    adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    adView.setAdSize(AdSize.BANNER)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Log.d("TAG", "initializing mobile ad sdk")

                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        AdViewContainer(adView)
                    }
                }

            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    private fun AdViewContainer(adView: AdView) {
        Log.d("TAG", "AdViewContainer")
        DisposableEffect(Unit) {
            initializeMobileAdsSdk {
                Log.d("TAG", "Mobile Ads SDK initialized")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Log.d("TAG", "loading banner")
                    loadBanner()
                    Log.d("TAG", "loading banner finished")
                }
            }
            onDispose { /* Cleanup, if necessary */ }
        }

        DisposableEffect(adLoaded) {
            Log.d("TAG", "ad loaded : $adLoaded")
            if (adLoaded) {
                Log.d("TAG", "Ad is ready to display")
            }
            onDispose { /* Cleanup, if necessary */ }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // AdView at the bottom center
            AndroidView(
                { adView },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { adView ->
                // Do nothing specific here, the ad is displayed
            }

            Spacer(modifier = Modifier.weight(1f))

            // Your app content goes here
            AuthNavGraph(googleSignInManager, signInResultLiveData)

        }
    }

    @RequiresApi(Build.VERSION_CODES.R)

    private fun loadBanner() {

        Log.d("TAG", "inside load banner")
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d("TAG", "Ad loaded successfully")
                adLoaded = true
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                // Gets the domain from which the error came.
                val errorDomain = error.domain
                // Gets the error code. See
                // https://developers.google.com/android/reference/com/google/android/gms/ads/AdRequest#constant-summary
                // for a list of possible codes.
                val errorCode = error.code
                // Gets an error message.
                // For example "Account not approved yet". See
                // https://support.google.com/admob/answer/9905175 for explanations of
                // common errors.
                val errorMessage = error.message
                // Gets additional response information about the request. See
                // https://developers.google.com/admob/android/response-info for more
                // information.
                val responseInfo = error.responseInfo
                // Gets the cause of the error, if available.
                val cause = error.cause
                // All of this information is available via the error's toString() method.
                Log.d("Ads", error.toString())

                Log.d("TAG", "Ad failed to load. Error code: $errorCode. Error message: $errorMessage")
            }
            override fun onAdOpened() {
                Log.d("TAG", "Ad opened")
            }
        }

    }
    private var isMobileAdsInitialized = false
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeMobileAdsSdk(callback: () -> Unit) {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {
            isMobileAdsInitialized = true
            callback.invoke()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == googleSignInManager !!.GOOGLE_SIGN_IN){
            googleSignInManager !!.handleSignInResult(data){ signInResult ->
                signInResultLiveData.value = signInResult
            }
        }
    }
}