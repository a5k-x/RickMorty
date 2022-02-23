package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.databinding.FragmentDetailsBinding
import com.learn.rickmorty.viewModel.DatailViewModel


private const val ARG_PARAM1 = "id"

class DetailsFragment : Fragment() {

    private var idCharacter: Int? = null
    private var vb: FragmentDetailsBinding? = null
    private val dataModel: DatailViewModel by lazy {
        DatailViewModel()
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
        vb = FragmentDetailsBinding.inflate(layoutInflater)
        return vb?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataModel.getLiveData().observe(viewLifecycleOwner, { it -> render(it) })
        dataModel.getRequestDatail(idCharacter)

    }

    fun render(data: AppState) {
        when (data) {
            is AppState.Success -> {}
            is AppState.Loading -> {}
            is AppState.Error -> {}
        }
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