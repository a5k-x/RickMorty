package com.learn.rickmorty.data.network


import com.learn.rickmorty.data.model.RequestApi
import retrofit2.Call

interface IListCharacterRepo {
    fun getListCharacter(page:Int): Call<RequestApi>
}