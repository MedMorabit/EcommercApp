package com.example.ecommercapp.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.ecommercapp.model.User
import com.example.ecommercapp.util.*
import com.example.ecommercapp.util.Constants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireBaseAuth:FirebaseAuth,
    private val db:FirebaseFirestore
) : ViewModel() {
    private val _register=MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register:Flow<Resource<User>> = _register
    private val _validate= Channel<RegisterFiledState>()
    val validate=_validate.receiveAsFlow()
    fun createAccountWithUserAndEmail(user:User, password:String){
    if(checkValidation(user.email,password)){
        fireBaseAuth.createUserWithEmailAndPassword(user.email,password)
            .addOnSuccessListener {
                runBlocking {
                    _register.emit(Resource.Loading())
                }
                it.user?.let {
                   saveUserInfo(it.uid,user)
                }
            }
            .addOnFailureListener {
                _register.value=Resource.Error(it.message.toString())

            }
    }else{
      val registerFiledState= RegisterFiledState(emailValidation(user.email), passwordValidation(password))
        runBlocking {
            _validate.send(registerFiledState)
        }

    }


    }

    private fun saveUserInfo(uid: String, user: User) {
        db.collection(USER_COLLECTION).document(uid).set(user).addOnSuccessListener {
            _register.value=Resource.Success(user)
        }.addOnFailureListener {
            _register.value=Resource.Error(it.message.toString())
        }

    }

    private fun checkValidation(email: String, password: String): Boolean {
        val emailVal = emailValidation(email)
        val passwordVal = passwordValidation(password)
        return emailVal is RegisterValidation.Success && passwordVal is RegisterValidation.Success

    }
}