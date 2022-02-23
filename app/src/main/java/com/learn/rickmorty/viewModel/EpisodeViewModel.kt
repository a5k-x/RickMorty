package com.learn.rickmorty.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.Episode
import com.learn.rickmorty.data.network.DataSourceRemote
import com.learn.rickmorty.data.repository.ListCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodeViewModel:ViewModel() {

    private var liveData = MutableLiveData<AppState>()
    private var scope = CoroutineScope(Dispatchers.IO)
    fun getLiveData() = liveData

    fun getEpisodes(array:List<Int>){

        scope.launch {
           liveData.postValue(AppState.Loading(1))
            val listEpisode = ListCharacterRepository(DataSourceRemote()).getListEpisodes(array)
            listEpisode.enqueue(object :Callback<List<Episode>>{
                override fun onResponse(
                    call: Call<List<Episode>>,
                    response: Response<List<Episode>>
                ) {
                    Log.i("AAA","Список объектов ${response.body()?.size}")
                    liveData.postValue(response.body()?.let { AppState.SuccessIdEpi(it) })
                }

                override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                    liveData.postValue(AppState.Error(t))
                }
            })
        }

    }

}