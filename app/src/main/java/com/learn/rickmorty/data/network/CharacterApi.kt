package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.RequestApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://cpt.ask-x.ru/te.html
//https://rickandmortyapi.com/api/character/?page=3

interface CharacterApi {
    @GET("character")
    fun getListCharacter(
        @Query("page") page:Int
    ): Call<RequestApi>


    @GET("character/{id}")
    fun getCharacter(
      @Path("id") id:Int
    ) :Call<Character>
}