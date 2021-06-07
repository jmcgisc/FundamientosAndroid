package io.keepcoding.eh_ho.extensions

import java.util.regex.Pattern

//https://stackoverflow.com/questions/23214434/regular-expression-in-android-for-password-field

fun CharSequence.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun CharSequence.isValidUsername(): Boolean {
    val passwordPattern = "[a-zA-Z0-9].{5,}\$"
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun CharSequence.isValidEmail(): Boolean {
    val passwordPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}