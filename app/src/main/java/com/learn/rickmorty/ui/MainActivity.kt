package com.learn.rickmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learn.rickmorty.R
import com.learn.rickmorty.databinding.ActivityMainBinding
import com.learn.rickmorty.ui.fragments.ListCharacterFragment

class MainActivity : AppCompatActivity() {

    private var vb: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vb = ActivityMainBinding.inflate(layoutInflater)

        setContentView(vb?.root)
        initFragment()
    }

    fun initFragment(){
        supportFragmentManager.beginTransaction()
            .replace(vb?.fragmentContainer?.id!!, ListCharacterFragment.newInstance())
            .commit()
    }
}