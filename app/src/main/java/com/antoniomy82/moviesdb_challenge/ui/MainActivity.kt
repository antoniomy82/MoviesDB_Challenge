package com.antoniomy82.moviesdb_challenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.utils.CommonUtil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load base fragment
        CommonUtil.replaceFragment(BaseFragment(),supportFragmentManager)
    }

}