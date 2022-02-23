package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.RequestApi
import retrofit2.Call


interface DataSource<T> {
   fun getData(page:Int):Call<RequestApi>
   fun getCharacter(id:Int):Call<Character>
}