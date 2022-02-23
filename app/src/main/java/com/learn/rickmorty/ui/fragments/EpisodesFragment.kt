package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.learn.rickmorty.R
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.databinding.FragmentEpisodesBinding
import com.learn.rickmorty.ui.adapters.MainAdapter
import com.learn.rickmorty.viewModel.EpisodeViewModel


private const val ARG_PARAM1 = "param1"


class EpisodesFragment : Fragment() {

    private var param1: Character? = null
    private var vb: FragmentEpisodesBinding? = null
    private var adapterM: MainAdapter? = null
    private val dataModel: EpisodeViewModel by lazy {
        EpisodeViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentEpisodesBinding.inflate(inflater, container, false)
        return vb?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listEpisodes: List<String>? = param1?.getEpisode()
        val listEpiCode = mutableListOf<Int>()

        if (listEpisodes != null) {
            for (i in listEpisodes.indices) {
                //не красиво
                var st =
                    listEpisodes[i].replace("https://rickandmortyapi.com/api/episode/", "", true)
                listEpiCode.add(st.toInt())
            }
        }
        dataModel.getLiveData().observe(viewLifecycleOwner, { it -> render(it) })
        dataModel.getEpisodes(listEpiCode)
        vb?.recyclerContaiterEpisode.also {
            it?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapterM = MainAdapter()
            it?.adapter = adapterM
        }


    }

    fun render(data: AppState) {
        when (data) {
            is AppState.SuccessIdEpi -> {
                Log.i("AAA", "Успех ${data.dataList[5].getType()}")

                adapterM?.initListEpisode(data.dataList)
                adapterM?.notifyDataSetChanged()
            }

            is AppState.Loading -> {
                Log.i("AAA", "Загрузка")
            }
            is AppState.Error -> {
                Log.i("AAA", "Ошибка - ${data.error.message}")
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Character) =
            EpisodesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}