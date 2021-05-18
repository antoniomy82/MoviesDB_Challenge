package com.antoniomy82.moviesdb_challenge.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.databinding.FragmentBaseBinding
import com.antoniomy82.moviesdb_challenge.model.Movie
import com.antoniomy82.moviesdb_challenge.ui.MoviesListAdapter
import java.lang.ref.WeakReference

class MoviesHomeViewModel : ViewModel() {

    //Main Fragment values
    private var frgBaseActivity: WeakReference<Activity>? = null
    private var frgBaseContext: WeakReference<Context>? = null
    private var frgBaseView: WeakReference<View>? = null
    private var mainBundle: Bundle? = null
    private var fragmentBaseBinding: FragmentBaseBinding ?=null


    private var recyclerView: WeakReference<RecyclerView>? = null
    var isFavourite: Boolean = false

    //Set Base fragment parameters in this VM
    fun setBaseFragmentBinding(
        frgActivity: Activity,
        frgContext: Context,
        frgView: View,
        mainBundle: Bundle?,
        fragmentBaseBinding: FragmentBaseBinding
    ) {
        this.frgBaseActivity = WeakReference(frgActivity)
        this.frgBaseContext = WeakReference(frgContext)
        this.frgBaseView = WeakReference(frgView)
        this.mainBundle = mainBundle
        this.fragmentBaseBinding=fragmentBaseBinding
    }


    //Set Recipes List in RecyclerView
    fun setMoviesRecyclerViewAdapter(movieList: List<Movie>) {

       // lastRecipesList = movieList

        recyclerView =
            WeakReference(frgBaseView?.get()?.findViewById(R.id.rvMovies) as RecyclerView)
        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(frgBaseActivity?.get()) //Orientation
        recyclerView?.get()?.layoutManager = manager
        movieList.sortedBy { it.title }
        recyclerView?.get()?.adapter = frgBaseContext?.get()?.let {
            MoviesListAdapter(
                this, movieList,
                it
            )
        }

        fragmentBaseBinding?.moviesHomeVM = this

    }

    fun makeFavoriteButton(mMovie: Movie) {

        Toast.makeText(frgBaseContext?.get(), "Recipe saved in favorite list", Toast.LENGTH_LONG)
            .show()

     //   frgMainContext?.get()?.let { mRepository.insertRecipe(it, mRecipe) }

    }

}