package com.learn.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.databinding.ItemCharacterBinding

class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listCharacter: List<Character> = listOf()

    fun initList(list:List<Character>){
        this.listCharacter = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)

    }

    inner class ViewHolder(val vb: ItemCharacterBinding):RecyclerView.ViewHolder(vb?.root) {
        fun bind(position: Int){
            val objectCharacter = listCharacter[position]
            vb?.imageCharacter.load(objectCharacter.getUrlImage())
            vb?.nameCharacter.text = objectCharacter.getName()
        }
    }

    override fun getItemCount() = listCharacter.size
}