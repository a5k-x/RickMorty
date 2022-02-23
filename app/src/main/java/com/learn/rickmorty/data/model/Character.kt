package com.learn.rickmorty.data.model


public class RequestApi(val results:List<Character>)

class Character(
    private val id:Int,
    private val name:String,
    private val status:String,
    private val species:String,
    private val type:String,
    private val gender:String,
    private val origin: Any,
    private val location:Any,
    private val image:String,
    private val episode:List<String>,
    private val url:String,
    private val created:String
    ){
    fun getId() = id
    fun getUrlImage() = image
    fun getName() = name
}