package com.learn.rickmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.network.DataSourceRemote
import com.learn.rickmorty.data.repository.ListCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private var liveData = MutableLiveData<AppState>()

    private var scope = CoroutineScope(Dispatchers.IO)

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun getRequestDatail(idCharacter: Int?) {
        scope.launch {
            val data =
                idCharacter?.let { ListCharacterRepository(DataSourceRemote()).getCharacter(it) }
            data?.enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    val data = response.body()
                    if (response != null) {
                        liveData.postValue(response.body()?.let { AppState.SuccessId(it) })
                    }
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    liveData.postValue(AppState.Error(t))
                }
            })
        }
    }

}