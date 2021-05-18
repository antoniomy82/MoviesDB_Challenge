package com.antoniomy82.moviesdb_challenge.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.antoniomy82.moviesdb_challenge.R

class CommonUtil {
    companion object {

        fun replaceFragment(fragment: Fragment?, fragmentManager: FragmentManager) {
            try {
                val transaction = fragmentManager.beginTransaction()
                fragment?.let { transaction.replace(R.id.frame_container, it) }
                transaction.commit()
            } catch (e: Exception) {
                Log.e("__replaceFragment", e.toString())
            }
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}