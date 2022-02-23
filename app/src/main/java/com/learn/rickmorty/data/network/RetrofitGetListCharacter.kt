package com.learn.rickmorty.data.network

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.DataModel
import com.learn.rickmorty.data.model.Episode
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitGetListCharacter : DataSource<Call<DataModel>> {

    private val baseUrl = "https://rickandmortyapi.com/api/"

    //Список персонажей
    override fun getData(page: Int): Call<DataModel> {
        return getService().getListCharacter(page)
    }

    //Персонаж
    override fun getCharacter(id: Int): Call<Character> {
        return getService().getCharacter(id)
    }

    //Эпизоды
    override fun getListEpisodes(array: List<Int>): Call<List<Episode>> {
        return getService().getEpisode(array)
    }

    private fun getService(): CharacterApi {
        return getListCharacter().create(CharacterApi::class.java)
    }

    private fun getListCharacter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}