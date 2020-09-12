package com.wsdydeni.module_home

import com.wsdydeni.library_base.network.BaseRepository
import com.wsdydeni.library_base.network.NetworkApiService
import kotlinx.coroutines.CoroutineScope

class HomeRepository(viewModelScope: CoroutineScope): BaseRepository(viewModelScope) {

    private val service by lazy { NetworkApiService.getService(HomeService::class.java) }

    suspend fun getBanner() = service.getBanner()
}

