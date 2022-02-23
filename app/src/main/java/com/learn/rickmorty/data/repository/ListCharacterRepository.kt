package com.learn.rickmorty.data.repository


import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.RequestApi
import com.learn.rickmorty.data.network.DataSource
import com.learn.rickmorty.data.network.IListCharacterRepo
import retrofit2.Call

class ListCharacterRepository(private val dataSource: DataSource<RequestApi>): IListCharacterRepo {
    override fun getListCharacter(page: Int): Call<RequestApi> {
        return dataSource.getData(page)
    }

    override fun getCharacter(id: Int): Call<Character> {
       return dataSource.getCharacter(id)
    }
}