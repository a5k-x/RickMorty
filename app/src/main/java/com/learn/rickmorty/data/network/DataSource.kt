package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.DataModel
import com.learn.rickmorty.data.model.Episide
import retrofit2.Call


interface DataSource<T> {
   fun getData(page:Int):Call<DataModel>
   fun getCharacter(id:Int):Call<Character>
   fun getListEpisodes(array:List<Int>):Call<List<Episide>>
}