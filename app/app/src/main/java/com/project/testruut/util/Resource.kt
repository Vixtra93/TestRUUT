package com.project.testruut.util

sealed class Resource<T>(val data: T?, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data = data, message = message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)


}