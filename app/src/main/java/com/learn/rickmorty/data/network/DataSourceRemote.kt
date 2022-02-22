package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.RequestApi
import retrofit2.Call

class DataSourceRemote(private val remoteProvider:RetrofitGetListCharacter = RetrofitGetListCharacter()):DataSource<RequestApi> {

    override fun getData(page: Int): Call<RequestApi> {
       return remoteProvider.getData(page)
    }
}