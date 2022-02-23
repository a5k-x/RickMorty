package com.learn.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.Episode
import com.learn.rickmorty.databinding.ItemCharacterBinding
import com.learn.rickmorty.databinding.ItemEpisodeBinding

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listCharacter: MutableList<Character> = mutableListOf()
    private var listEpisode = mutableListOf<Episode>()
    lateinit var onClickHandler: OnClickItem

    fun onClick(listener: OnClickItem) {
        this.onClickHandler = listener
    }

    fun initList(list: List<Character>) {
        this.listCharacter.addAll(list.toMutableList())
    }

    fun initListEpisode(list: List<Episode>) {

        this.listEpisode.addAll(list.toMutableList())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CHARACTER -> ViewHolder(
                ItemCharacterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> {
                ViewHolderEpisode(
                    ItemEpisodeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_CHARACTER -> {
                (holder as ViewHolder).bind(position)
            }
            else -> {
                (holder as ViewHolderEpisode).bind(position)
            }
        }

        (holder as ViewHolder).bind(position)


    }

    inner class ViewHolder(val vb: ItemCharacterBinding) : RecyclerView.ViewHolder(vb?.root) {
        fun bind(position: Int) {
            val objectCharacter = listCharacter[position]
            vb?.imageCharacter.load(objectCharacter.getUrlImage())
            vb?.nameCharacter.text = objectCharacter.getName()
            itemView.setOnClickListener {
                onClickHandler.onClick(objectCharacter)
            }
        }
    }

    inner class ViewHolderEpisode(val vb: ItemEpisodeBinding) : RecyclerView.ViewHolder(vb?.root) {
        fun bind(position: Int) {

            vb?.nameEpisodeCard.text = listEpisode[position].getName()
            vb?.dateEpisodeCard.text = listEpisode[position].getData()

        }

    }

    override fun getItemViewType(position: Int): Int {
        return when {
            listEpisode.isNullOrEmpty() -> TYPE_CHARACTER
            else -> TYPE_EPISODE
        }
    }



    override fun getItemCount() = listCharacter.size

    companion object {
        private const val TYPE_CHARACTER = 0
        private const val TYPE_EPISODE = 1
    }

}


interface OnClickItem {
    fun onClick(itemCharacter: Character)
}