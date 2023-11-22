package com.example.recipebook.presentation.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthentication.data.AuthRepository
import com.example.firebaseauthentication.presentation.signup_screen.SignUpState
//import com.example.recipebook.presentation.login_screen.SignUpState
import com.example.firebaseauthentication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository:AuthRepository
) : ViewModel() {

    val _signUpState = Channel<SignUpState> ()
    val signUpState = _signUpState.receiveAsFlow()

    fun loginUser(email:String, password:String) = viewModelScope.launch {
        repository.loginUser(email, password).collect {
                result->when(result){
            is Resource.Success->{
                _signUpState.send(SignUpState(isSuccess = "Signed In Successfully"))

            }is Resource.Loading->{
                _signUpState.send(SignUpState(isLoading = true))
            }is Resource.Error->{
                _signUpState.send(SignUpState(isError = result.message))
            }

            else -> {}
        }
        }
    }

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        _signUpState.send(SignUpState(isLoading = true))

        try {
            repository.registerUser(email, password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _signUpState.send(SignUpState(isSuccess = "Registered Successfully"))
                    }
                    is Resource.Error -> {
                        _signUpState.send(SignUpState(isError = result.message))
                    }
                    else -> {}
                }
            }
        } catch (e: Exception) {
            _signUpState.send(SignUpState(isError = e.message ?: "An error occurred"))
        }
    }

}