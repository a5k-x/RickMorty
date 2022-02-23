package com.learn.rickmorty.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.learn.rickmorty.R
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.databinding.FragmentDetailsBinding
import com.learn.rickmorty.viewModel.DetailViewModel


private const val ARG_PARAM1 = "id"

class DetailsFragment : Fragment() {

    private var idCharacter: Int? = null
    private var vb: FragmentDetailsBinding? = null
    private val dataModel: DetailViewModel by lazy {
        DetailViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCharacter = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentDetailsBinding.inflate(inflater,container,false)
        return vb?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataModel.getLiveData().observe(viewLifecycleOwner, { it -> render(it) })
        dataModel.getRequestDatail(idCharacter)

    }

    @SuppressLint("ResourceType")
    fun render(data: AppState) {
        when (data) {
            is AppState.SuccessId -> {
                vb?.imageCharacterCard?.load(data.dataList.getUrlImage())
                vb?.nameCharacterCard?.text = data.dataList.getName()
                val type = data.dataList
                vb?.typeCharacterCard?.text = "Тип: " + type.getType() ?: "Неопределенно"
                vb?.statusCharacterCard?.text = "Статус: " + type.getStatus() ?: "Неопределенно"
                //Местоположения
                var location = type.getLocation()
                vb?.locationCharacterCard?.text =
                    "Местоположение: " + location.getName() ?: "Неопределенно"
                vb?.episodesCharacterCard?.setOnClickListener {
                    openEpisodes(type)
                }
            }
            is AppState.Loading -> {
                Log.i("AAA", "Загрузка")
            }
            is AppState.Error -> {
                Log.i("AAA", "Ошибка - ${data.error.message}")
            }
        }
    }

    //Открыть список эпизодов
    private fun openEpisodes(type: Character) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.fragment_container,EpisodesFragment.newInstance(type))
            ?.commit()

    }

    companion object {
        @JvmStatic
        fun newInstance(idCharacter: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, idCharacter)
                }
            }
    }
}