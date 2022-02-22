package com.learn.rickmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learn.rickmorty.R
import com.learn.rickmorty.databinding.FragmentListCharacterBinding


class ListCharacterFragment : Fragment() {

    private var vb: FragmentListCharacterBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentListCharacterBinding.inflate(layoutInflater)
        return vb?.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ListCharacterFragment()

    }
}