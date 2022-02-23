package com.learn.rickmorty.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.RequestApi
import com.learn.rickmorty.data.network.DataSourceRemote
import com.learn.rickmorty.data.repository.ListCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListCharacterViewModel:ViewModel() {

    private var scope = CoroutineScope(Dispatchers.IO)

    private var liveData = MutableLiveData<AppState>()

    fun getLiveData():LiveData<AppState>{
        return liveData
    }
//Запрос на получение списка персонажей
    fun getListCharacter(page:Int){

        scope.launch {
        val  listCharacter = ListCharacterRepository(DataSourceRemote()).getListCharacter(page)
         withContext(Dispatchers.Main){
             liveData.postValue(AppState.Loading(1))
             listCharacter.enqueue(object :
                 Callback<RequestApi>{
                 override fun onResponse(call: Call<RequestApi>, response: Response<RequestApi>) {
                     Log.i("AAA", "Ответ от сервера: ${response.body()?.results?.size}")
                     liveData.postValue(response.body()?.let { AppState.Success(it.results) })

                 }

                 override fun onFailure(call: Call<RequestApi>, t: Throwable) {
                     Log.i("AAA", "Ошибка${t.message}")
                     liveData.postValue(AppState.Error(t))
                 }

             })
         }
        }
    }

}