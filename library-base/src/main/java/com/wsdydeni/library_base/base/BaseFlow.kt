package com.wsdydeni.library_base.base

import com.wsdydeni.library_base.ext.logD
import com.wsdydeni.library_base.ext.logE
import com.wsdydeni.library_base.network.*
import com.wsdydeni.library_base.utils.ExceptionUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * 失败需要和UI关联(共同处理)
 */
suspend fun <T> relateViewCommon(request: suspend () -> BaseResponse<T>): Flow<T> {
    return flow {
        executeResponse(request()).suspendOnSuccess {
            emit(data)
        }.onFailure {
            "relateViewCommon onFailure==${message()}".logE()
        }
    }.catch { e ->
        e.printStackTrace()
        val apiException = ExceptionUtil.getApiException(e)
        "relateViewCommon catch==${apiException.errorMessage + apiException.errorCode}".logE()
    }
}

/**
 * 失败不需要和UI关联
 */
suspend fun <T> flowNormal(request: suspend () -> BaseResponse<T>): Flow<T> {
    return flow {
        "flowNormal onThread=${Thread.currentThread().name}".logD("Basic-->NetWorkThread")
        executeResponse(request()).suspendOnSuccess {
            emit(data)
        }.onFailure {
            "flowNormal onFailure==${message()}".logE()
        }
    }.catch {
        val apiException = ExceptionUtil.getApiException(it)
        "flowNormal CATCH == ${apiException.errorMessage + apiException.errorCode}".logE()
    }
}

/**
 * 需要单独处理和UI关联的失败的请求
 */
suspend fun <T> associatedView(
    request: suspend () -> BaseResponse<T>,
    onError: suspend (ApiException) -> Unit
): Flow<T> {
    return flow {
        "flowNormal onThread=${Thread.currentThread().name}".logD("Basic-->NetWorkThread")
        executeResponse(request()).suspendOnSuccess {
            emit(data)
        }.suspendOnFailure {
            onError(ApiException(response.getResponseMsg(), response.getResponseCode()))
            "flowNormal onFailure==${message()}".logE()
        }
    }.catch {
        val apiException = ExceptionUtil.getApiException(it)
        "flowNormal CATCH==${apiException.errorMessage + apiException.errorCode}".logE()
    }
}