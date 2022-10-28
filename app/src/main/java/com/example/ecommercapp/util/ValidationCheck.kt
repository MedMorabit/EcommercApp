package com.example.ecommercapp.util

import android.util.Patterns
import java.util.regex.Pattern

fun emailValidation(email:String):RegisterValidation{
    if(email.isEmpty())
        return RegisterValidation.Failed("email cannot be empty")
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("enter a valid email")
    return RegisterValidation.Success
}
fun passwordValidation(password:String):RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("password cannot be empty")
    if(password.length<6)
        return RegisterValidation.Failed("password should contains more than 6 char")

    return RegisterValidation.Success
}