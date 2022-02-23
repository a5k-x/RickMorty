package com.learn.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.databinding.ItemCharacterBinding

class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listCharacter: MutableList<Character> = mutableListOf()
    lateinit var onClickHandler: OnClickItem

    fun onClick(listener:OnClickItem){
        this.onClickHandler = listener
    }

    fun initList(list:List<Character>){
        this.listCharacter.addAll(list.toMutableList())
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
            itemView.setOnClickListener {
                onClickHandler.onClick(objectCharacter)
            }
        }
    }

    override fun getItemCount() = listCharacter.size
}

interface OnClickItem{
    fun onClick(itemCharacter: Character)
}