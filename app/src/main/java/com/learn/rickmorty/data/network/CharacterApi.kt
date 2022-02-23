package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.DataModel
import com.learn.rickmorty.data.model.Episide
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://cpt.ask-x.ru/te.html
//https://rickandmortyapi.com/api/character/?page=3

interface CharacterApi {
    @GET("character")
    fun getListCharacter(
        @Query("page") page: Int
    ): Call<DataModel>

    @GET("character/{id}")
    fun getCharacter(
        @Path("id") id: Int
    ): Call<Character>

    @GET("episode/{id}")
    fun getEpisode(
        @Path("id") id: List<Int>
    ): Call<List<Episide>>
}