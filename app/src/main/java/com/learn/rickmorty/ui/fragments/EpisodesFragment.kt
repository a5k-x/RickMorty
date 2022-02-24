package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.rickmorty.R
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.data.model.Episode
import com.learn.rickmorty.databinding.FragmentEpisodesBinding
import com.learn.rickmorty.ui.adapters.SecondAdapter
import com.learn.rickmorty.viewModel.EpisodeViewModel


private const val ARG_PARAM1 = "param1"


class EpisodesFragment : Fragment() {

    private var param1: Character? = null
    private var vb: FragmentEpisodesBinding? = null
    private var adapterM: SecondAdapter? = null
    private val dataModel: EpisodeViewModel by lazy {
        EpisodeViewModel()
    }
    private var textError: String? = null
    private var spinner: ProgressBar? = null
    private var btnSorted: AppCompatImageView? = null
    private var clickBoolean: Boolean = true
    lateinit var listEpisode: List<Episode>
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
            adapterM = SecondAdapter()
            it?.adapter = adapterM
        }
        initVariable()
    }

    private fun render(data: AppState) {
        when (data) {
            is AppState.SuccessIdEpi -> {
                spinner?.visibility = View.GONE
                listEpisode = data.dataList
                adapterM?.initListEpisode(listEpisode)
                adapterM?.notifyDataSetChanged()
            }
            is AppState.Loading -> {
                spinner?.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                spinner?.visibility = View.GONE
                Toast.makeText(requireContext(), textError, Toast.LENGTH_LONG)
                    .show()
                Log.i("AAA", "Ошибка - ${data.error.message}")
            }
        }
    }

    private fun initVariable() {
        spinner = vb?.spinnerProgressBar
        textError = resources.getString(R.string.textError)
        btnSorted = vb?.sortData
        btnSorted?.setOnClickListener {
            if (clickBoolean) {
                dataModel.getBySortedListEpisodeDesc(listEpisode)
                clickBoolean = false
            } else {
                dataModel.getBySortedListEpisodeAsc(listEpisode)
                clickBoolean = true
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        dataModel.closeScope()
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