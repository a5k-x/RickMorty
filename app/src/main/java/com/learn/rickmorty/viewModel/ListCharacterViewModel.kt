package com.learn.rickmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.DataModel
import com.learn.rickmorty.data.network.DataSourceRemote
import com.learn.rickmorty.data.repository.ListCharacterRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListCharacterViewModel : ViewModel() {

    private var scope = CoroutineScope(Dispatchers.IO)

    private var liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    //Запрос на получение списка персонажей
    fun getListCharacter(page: Int) {

        scope.launch {
            val listCharacter = ListCharacterRepository(DataSourceRemote()).getListCharacter(page)

                liveData.postValue(AppState.Loading(1))
                listCharacter.enqueue(object :
                    Callback<DataModel> {
                    override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                        liveData.postValue(response.body()?.let { AppState.Success(it.results) })
                    }

                    override fun onFailure(call: Call<DataModel>, t: Throwable) {
                        liveData.postValue(AppState.Error(t))
                    }

                })

        }
    }

    fun closeScope(){
        scope.cancel()
    }
}