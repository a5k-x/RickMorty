package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
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
    lateinit var spinner: ProgressBar
    private var textError: String? = null

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
                openDateilCharacterFragment(itemCharacter)
            }
        })
        initVariable()
        scrollCust()
    }

    private fun initVariable() {
        spinner = vb?.spinnerProgressBar!!
        textError = resources.getString(R.string.textError)
    }

    //Открыть детализацию по персонажу
    fun openDateilCharacterFragment(character: Character) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.fragment_container, DetailsFragment.newInstance(character.getId()))
            ?.commit()
    }

    private fun scrollCust() {
        vb?.recyclerContainer?.setOnScrollChangeListener { v, _, _, _, _ ->
            val view = (v as RecyclerView).layoutManager
            //кол-во элементов в контейнере
            val asd1 = view?.itemCount!!
            //последний отображаемый элемент
            val asd2 = (view as LinearLayoutManager).findLastVisibleItemPosition()
            //Для пагинации
            if (asd1.minus(asd2) > 5) {
                isLoad = true
            }
            if (asd1.minus(asd2) <= 5 && isLoad) {
                newPage()
            }
            Log.i("AAA", "Скрол: page $page ")
        }
    }

    private fun newPage() {
        isLoad = false
        dataModel.getListCharacter(++page)
    }

    //Обработка состояний
    private fun render(data: AppState) {
        when (data) {
            is AppState.Success -> {
                spinner.visibility = View.GONE
                adapter1?.initList(data.dataList)
                adapter1?.notifyDataSetChanged()
            }
            is AppState.Loading -> spinner.visibility = View.VISIBLE
            is AppState.Error -> {
                spinner.visibility = View.GONE
                Toast.makeText(requireContext(), textError, Toast.LENGTH_LONG)
                    .show()
                Log.i("AAA", "Ошибка - ${data.error.message}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataModel.closeScope()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListCharacterFragment()
    }
}