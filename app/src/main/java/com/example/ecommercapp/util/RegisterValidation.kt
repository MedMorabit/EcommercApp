package com.example.ecommercapp.util

sealed class RegisterValidation{
    object Success:RegisterValidation()
    data class Failed(val message:String):RegisterValidation()

}
data class RegisterFiledState(
    val email:RegisterValidation,
    val password:RegisterValidation

)
