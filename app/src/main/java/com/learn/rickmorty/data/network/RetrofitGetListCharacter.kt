package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.RequestApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitGetListCharacter : DataSource<Call<RequestApi>> {

    private val baseUrl = "https://rickandmortyapi.com/api/"

    override fun getData(page: Int): Call<RequestApi> {
        return getService(page).getListCharacter(page)
    }

    private fun getService(page: Int): CharacterApi {
        return getListCharacter().create(CharacterApi::class.java)
    }

    private fun getListCharacter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}