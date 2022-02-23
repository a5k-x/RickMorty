package com.learn.rickmorty.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


class DataModel(val results: List<Character>)
@Parcelize
open class Character(
    private val id: Int,
    private val name: String,
    private val status: String,
    private val species: String,
    private val type: String,
    private val gender: String,
    private val origin: Origin,
    private val location: Location,
    private val image: String,
    private val episode: List<String>,
    private val url: String,
    private val created: String
) : Parcelable {
    fun getId() = id
    fun getUrlImage() = image
    fun getName() = name
    fun getType() = type
    fun getStatus() = status
    fun getLocation(): Location = location
    fun getEpisode(): List<String> = episode
    fun getOrigin(): Origin = origin
}

//Имя и ссылка на последнюю известную конечную точку местоположения персонажа.
@Parcelize
class Location(
    private val name: String,
    private val url: String,

) : Parcelable {
    fun getName()= name
    fun getUrl()= url
}

//Имя и ссылка на место происхождения персонажа.
@Parcelize
class Origin(
    private val name: String,
    private val url: String
) : Parcelable


class Episode(
    private val name: String ="",
    private val air_date:String = "",
    private val type_in:Int = 1
){
    fun getName() = name
    fun getData() = air_date
    fun getType() = type_in
}