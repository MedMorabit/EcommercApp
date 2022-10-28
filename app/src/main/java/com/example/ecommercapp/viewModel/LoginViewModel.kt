package com.example.ecommercapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercapp.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth:FirebaseAuth
) :ViewModel() {
    private var _login= MutableSharedFlow<Resource<FirebaseUser>>()
    val login=_login.asSharedFlow()
    private var _validate= Channel<RegisterFiledState>()
    var validate=_validate.receiveAsFlow()
    private var _resetPassword= MutableSharedFlow<Resource<String>>()
     var resetPassword=_resetPassword.asSharedFlow()

    fun login(email:String,password:String){
        if(checkLoginValidation(email,password)) {
            runBlocking {
                _login.emit(Resource.Loading())
            }
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                viewModelScope.launch {
                    it.user.let {
                        _login.emit(Resource.Success(it))
                    }
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Resource.Error(it.message.toString()))
                }
            }
        }else{
            val registerFiledState=RegisterFiledState(emailValidation(email), passwordValidation(password))
            runBlocking {
                _validate.send(registerFiledState)
            }
        }
    }

    private fun checkLoginValidation(email: String, password: String) :Boolean {
        val checkEmail= emailValidation(email)
        val checkPassword= passwordValidation(password)
        return checkEmail is RegisterValidation.Success && checkPassword is RegisterValidation.Success

    }
    fun resetPassword(email:String){
        viewModelScope.launch {
            _resetPassword.emit(Resource.Loading())
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                viewModelScope.launch {
                    _resetPassword.emit(Resource.Success(email))
                }
            }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _resetPassword.emit(Resource.Error(it.message.toString()))

                    }
                }
        }
    }

}