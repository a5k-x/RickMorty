package com.learn.rickmorty.data.repository


import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.DataModel
import com.learn.rickmorty.data.model.Episode
import com.learn.rickmorty.data.network.DataSource
import com.learn.rickmorty.data.network.IListCharacterRepo
import retrofit2.Call

class ListCharacterRepository(private val dataSource: DataSource<DataModel>): IListCharacterRepo {
    override fun getListCharacter(page: Int): Call<DataModel> {
        return dataSource.getData(page)
    }

    override fun getCharacter(id: Int): Call<Character> {
       return dataSource.getCharacter(id)
    }

    override fun getListEpisodes(array: List<Int>): Call<List<Episode>> {
        return dataSource.getListEpisodes(array)
    }
}