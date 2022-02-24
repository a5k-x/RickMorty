package com.learn.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.rickmorty.data.model.Episode
import com.learn.rickmorty.databinding.ItemEpisodeBinding

class SecondAdapter : RecyclerView.Adapter<SecondAdapter.ViewHolder>() {

    private var listEpisode = mutableListOf<Episode>()

    fun initListEpisode(list: List<Episode>) {
        this.listEpisode.clear()
        this.listEpisode.addAll(list.toMutableList())

    }

    inner class ViewHolder(val vb: ItemEpisodeBinding) : RecyclerView.ViewHolder(vb?.root) {
        fun bind(position: Int) {
            vb?.nameEpisodeCard.text = "Эпизод: " + listEpisode[position].getName()
            vb?.dateEpisodeCard.text = "Дата выхода" + listEpisode[position].getData()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondAdapter.ViewHolder {
        return ViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SecondAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = listEpisode.size
}