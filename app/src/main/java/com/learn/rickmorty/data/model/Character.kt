package com.learn.rickmorty.data.model

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*


class DataModel(val results: List<Character>)

@Parcelize
open class Character(
    private val id: Int,
    private val name: String,
    private val status: String,
    private val type: String,
    private val location: Location,
    private val image: String,
    private val episode: List<String>,
) : Parcelable {
    fun getId() = id
    fun getUrlImage() = image
    fun getName() = name
    fun getType() = type
    fun getStatus() = status
    fun getLocation(): Location = location
    fun getEpisode(): List<String> = episode

}

//Имя и ссылка на последнюю известную конечную точку местоположения персонажа.
@Parcelize
class Location(
    private val name: String,
    private val url: String,

    ) : Parcelable {
    fun getName() = name
    fun getUrl() = url
}

//Имя и ссылка на место происхождения персонажа.
@Parcelize
class Origin(
    private val name: String,
    private val url: String
) : Parcelable

class Episode(
    private val name: String = "",
    private val air_date: String = "",
    private var dataNew: Date? = null

) {
    fun getName() = name
    fun getData() = air_date
    fun getDataNew() = dataNew

    //из строки  объект дата, для сортировки
    fun getDataNew(air_date: String) {
        val simpData = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        dataNew = simpData.parse(air_date)

    }
}