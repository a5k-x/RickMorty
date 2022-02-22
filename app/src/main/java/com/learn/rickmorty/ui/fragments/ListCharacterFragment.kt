package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.databinding.FragmentListCharacterBinding
import com.learn.rickmorty.ui.adapters.MainAdapter
import com.learn.rickmorty.viewModel.ListCharacterViewModel


class ListCharacterFragment : Fragment() {

    private var vb: FragmentListCharacterBinding? = null
    private var adapter1: MainAdapter? = null
    private val dataModel: ListCharacterViewModel by lazy {
        ListCharacterViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentListCharacterBinding.inflate(layoutInflater)
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataModel.getLiveData().observe(viewLifecycleOwner, { it -> render(it) })
        dataModel.getListCharacter(1)
        vb?.recyclerContainer?.run {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, false)
            adapter1 = MainAdapter()
            adapter = adapter1
        }
    }

    fun render(data: AppState) {
        when (data) {
            is AppState.Success -> {
                Log.i("AAA", "кол-во объектов${data.dataList.size}")
                adapter1?.initList(data.dataList)
                adapter1?.notifyDataSetChanged()
            }
            is AppState.Loading ->
                Log.i("AAA", "Загрузка")
            is AppState.Error ->
                Log.i("AAA", "Ошибка - ${data.error.message}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListCharacterFragment()
    }
}