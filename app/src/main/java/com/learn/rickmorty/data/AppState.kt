package com.learn.rickmorty.data

import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.Episode

sealed class AppState{
    data class Success(val dataList: List<Character>):AppState()
    data class SuccessId(val dataList: Character):AppState()
    data class SuccessIdEpi(val dataList: List<Episode>):AppState()
    data class Loading(val progress:Int):AppState()
    data class Error(val error:Throwable):AppState()
}
