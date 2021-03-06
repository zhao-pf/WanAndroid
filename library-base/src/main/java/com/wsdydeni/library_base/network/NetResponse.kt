package com.wsdydeni.library_base.network

sealed class NetResponse<out T> {

    data class Success<T>(val value: BaseResponse<T>) : NetResponse<T>() {
        val data = value.getResponseData()
        override fun toString() = "[NetResponse.Success](data=$value)"
    }

    data class Failure<T>(val response: BaseResponse<T>) : NetResponse<T>() {
        private val errorCode = response.getResponseCode()
        private val errorMsg = response.getResponseMsg()
        override fun toString(): String =
            "[NetResponse.Failure] (errorCode=$errorCode errorMsg=$errorMsg)"
    }

    data class Exception<T>(val exception: ApiException) : NetResponse<T>() {
        private val errorMsg: String? = exception.errorMessage
        private val errorCode = exception.errorCode
        override fun toString(): String =
            "[NetResponse.Exception](errorCode=$errorCode errorMsg=$errorMsg)"
    }

}