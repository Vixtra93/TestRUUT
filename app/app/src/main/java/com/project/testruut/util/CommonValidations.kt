package com.project.testruut.util

import android.util.Patterns

fun String.isValidPassword(): Boolean {
    if (this.length < 8) return false
    if (this.filter { it.isDigit() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isUpperCase() }
            .firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isLowerCase() }
            .firstOrNull() == null) return false
    if (this.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
