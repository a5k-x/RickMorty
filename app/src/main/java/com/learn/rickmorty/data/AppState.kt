package com.learn.rickmorty.data

import com.learn.rickmorty.data.model.Character

sealed class AppState{
    data class Success(val dataList: List<Character>):AppState()
    data class Loading(val progress:Int):AppState()
    data class Error(val error:Throwable):AppState()
}
