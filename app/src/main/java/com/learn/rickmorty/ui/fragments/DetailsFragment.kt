package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.ImageLoader
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
    private var spinner: ProgressBar? = null
    private var imadeCharacter: ImageView? = null
    private var nameCharacter: TextView? = null
    private var typeCharacter: TextView? = null
    private var statusCharacter: TextView? = null
    private var locationCharacter: TextView? = null
    private var episodesCharacter: TextView? = null
    private var textError:String? = null


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
        vb = FragmentDetailsBinding.inflate(inflater, container, false)
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataModel.getLiveData().observe(viewLifecycleOwner, { it -> render(it) })
        dataModel.getRequestDatail(idCharacter)
        initVariable()
    }

    fun initVariable() {
        spinner = vb?.spinnerProgressBar
        imadeCharacter = vb?.imageCharacterCard
        nameCharacter = vb?.nameCharacterCard
        typeCharacter = vb?.typeCharacterCard
        statusCharacter = vb?.statusCharacterCard
        locationCharacter = vb?.locationCharacterCard
        episodesCharacter = vb?.episodesCharacterCard
        textError = resources.getString(R.string.textError)
    }

    private fun render(data: AppState) {
        when (data) {
            is AppState.SuccessId -> {
                spinner?.visibility = View.GONE
                loadImage(data.dataList)
                nameCharacter?.text = data.dataList.getName()
                val type = data.dataList
                typeCharacter?.text = "Вид: " + type.getType()
                statusCharacter?.text = "Статус: " + type.getStatus()
                //Местоположения
                var location = type.getLocation()
                locationCharacter?.text =
                    "Местоположение: " + location.getName()
                episodesCharacter?.text = resources.getText(R.string.text_next_more_episodes)
                episodesCharacter?.setOnClickListener {
                    openEpisodes(type)
                }
            }
            is AppState.Loading -> spinner?.visibility = View.VISIBLE
            is AppState.Error -> {
                spinner?.visibility = View.GONE

                Toast.makeText(requireContext(), textError, Toast.LENGTH_LONG)
                    .show()
                Log.i("AAA", "Ошибка - ${data.error.message}")
            }
        }
    }

    //Загрузка изображения
    private fun loadImage(data: Character) {
        val imageLoader = ImageLoader.Builder(requireContext())
            .crossfade(true)
            .error(R.drawable.icon_error)
            .placeholder(R.drawable.icon_download_image)
            .build()
        imadeCharacter?.load(data.getUrlImage(), imageLoader)
    }

    //Открыть список эпизодов
    private fun openEpisodes(type: Character) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.fragment_container, EpisodesFragment.newInstance(type))
            ?.commit()

    }
    override fun onDestroy() {
        super.onDestroy()
        dataModel.closeScope()
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