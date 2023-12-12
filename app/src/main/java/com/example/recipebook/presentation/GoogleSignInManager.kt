package com.example.recipebook.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.recipebook.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInManager private constructor() {
    private var context: Context? = null
    private var activity: Activity?=null
    private var mGoogleSignInClient:GoogleSignInClient? = null
    val GOOGLE_SIGN_IN = 100
    private  var mAuth:FirebaseAuth?=null

    private fun init(context: Context){
        this.context=context
        activity=context as Activity
    }
    companion object{
        private var instance:GoogleSignInManager?=null
        fun getInstance(context: Context):GoogleSignInManager?{
            if(instance == null){
                instance= GoogleSignInManager()
            }
            instance!!.init(context)
            return instance
        }
    }
    fun setupGoogleSignInOptions(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(
                context!!.resources.getString(R.string.web_client_id) + "..googleusercontent.com"
            )
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(context!!, gso)
        mAuth = FirebaseAuth.getInstance()
    }
    val isUserAlreadySignIn:Boolean
        get(){
            val currentUser = mAuth!!.currentUser
            return currentUser != null
        }

    fun signIn(){
        Log.d("TAG", "signIn icon clicked")
        val signInIntent = mGoogleSignInClient!!.signInIntent
        activity!!.startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }
    fun signOut(){
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient!!.signOut()
        Toast.makeText(context, "Signed Out.", Toast.LENGTH_SHORT).show()
    }

    val profileInfo:FirebaseUser?
        get(){
            val account = FirebaseAuth.getInstance().currentUser
            if(account != null){

            }else{
                Toast.makeText(context, "No account info found.", Toast.LENGTH_SHORT).show()

            }
            return account
        }
    fun handleSignInResult(data: Intent?) = try{
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        firebaseAuthWithGoogle(account.idToken)
    }catch (e:ApiException){
        Log.w("TAG", "signInResult:failed code = " + e.statusCode)
        Toast.makeText(context, "Sign In Failed", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "SignIn fAILED")
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener{
                task->
                if(task.isSuccessful){
                    //SignIn Success
                    Log.d("TAG", "signInWithCredential: SUCCESS")
                    val user = mAuth!!.currentUser
                    Toast.makeText(context,"Signed i Successfully", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }

    }
}