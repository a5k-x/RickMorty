package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.DataModel
import com.learn.rickmorty.data.model.Episide
import retrofit2.Call

class DataSourceRemote(private val remoteProvider:RetrofitGetListCharacter = RetrofitGetListCharacter()):DataSource<DataModel> {

    override fun getData(page: Int): Call<DataModel> {
       return remoteProvider.getData(page)
    }

    override fun getCharacter(id: Int): Call<Character> {
        return remoteProvider.getCharacter(id)
    }

    override fun getListEpisodes(array: List<Int>): Call<List<Episide>> {
        return remoteProvider.getListEpisodes(array)
    }
}