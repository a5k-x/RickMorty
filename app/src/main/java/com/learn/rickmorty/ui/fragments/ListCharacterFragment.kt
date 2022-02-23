package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.rickmorty.R
import com.learn.rickmorty.data.AppState
import com.learn.rickmorty.data.model.Character
import com.learn.rickmorty.databinding.FragmentListCharacterBinding
import com.learn.rickmorty.ui.adapters.MainAdapter
import com.learn.rickmorty.ui.adapters.OnClickItem
import com.learn.rickmorty.viewModel.ListCharacterViewModel

class ListCharacterFragment : Fragment() {

    private var vb: FragmentListCharacterBinding? = null
    private var adapter1: MainAdapter? = null
    private var page: Int = 1
    private var isLoad: Boolean = false
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
        dataModel.getListCharacter(page)
        vb?.recyclerContainer?.run {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, false)
            adapter1 = MainAdapter()
            adapter = adapter1
        }
        adapter1?.onClick(object : OnClickItem {
            override fun onClick(itemCharacter: Character) {
                Log.i("AAA", "Имя персонажа ${itemCharacter.getName()}")
                openDateilCharacterFragment(itemCharacter)
            }
        })
        testScrol()
    }

    fun openDateilCharacterFragment(character: Character) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.add(R.id.fragment_container, DetailsFragment.newInstance(character.getId()))
            ?.commit()
    }

    fun testScrol() {
        vb?.recyclerContainer?.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                val view = (v as RecyclerView).layoutManager
                //кол-во элементов в контейнере
                var asd1 = view?.itemCount!!
                //последний отображаемый элемент
                var asd2 = (view as LinearLayoutManager).findFirstVisibleItemPosition()
                //Временно для пагинации
                if (asd1.minus(asd2) > 5) {
                    isLoad = true
                }
                if (asd1.minus(asd2) <= 5 && isLoad) {
                    newPage()
                }
                Log.i("AAA", "Скрол: page $page ")
            }
        })
    }

    fun newPage() {
        isLoad = false
        dataModel.getListCharacter(++page)
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