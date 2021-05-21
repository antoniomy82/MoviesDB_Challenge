package com.antoniomy82.moviesdb_challenge.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.databinding.FragmentBaseBinding
import com.antoniomy82.moviesdb_challenge.model.Movie
import com.antoniomy82.moviesdb_challenge.model.database.LocalDbRepository
import com.antoniomy82.moviesdb_challenge.model.network.MovieDbRepository
import com.antoniomy82.moviesdb_challenge.model.network.MoviesList
import com.antoniomy82.moviesdb_challenge.ui.BaseFragment
import com.antoniomy82.moviesdb_challenge.ui.MoviesListAdapter
import com.antoniomy82.moviesdb_challenge.utils.CommonUtil
import java.lang.ref.WeakReference

class MoviesHomeViewModel : ViewModel() {

    //Base Fragment parameters
    private var frgBaseActivity: WeakReference<Activity>? = null
    private var frgBaseView: WeakReference<View>? = null
    private var mainBundle: Bundle? = null
    private var fragmentBaseBinding: FragmentBaseBinding? = null
    var frgBaseContext: WeakReference<Context>? = null

    //Recycler view variables
    private var recyclerView: WeakReference<RecyclerView>? = null
    var savedMovies = mutableListOf<String>()
    private var actualMovieList: MutableList<Movie>? = null

    //Control variables
    var isTopSelected = true
    var isFavourite: Boolean = false  //To Show & hide favourite button
    var lastMoviesList: MoviesList? = null
    private var searchMode = false


    //Repository variables
    val localRepository = LocalDbRepository()
    val networkRepository = MovieDbRepository()

    //Live data parameters
    val savedFavouritesMovies = MutableLiveData<List<Movie>>()
    val retrieveNetworkMovies = MutableLiveData<MoviesList>()
    val savedList = MutableLiveData<List<String>>()


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
        this.fragmentBaseBinding = fragmentBaseBinding
    }


    //Set Recipes List in RecyclerView
    fun setMoviesRecyclerViewAdapter(movieList: List<Movie>) {

        actualMovieList = movieList.toMutableList()

        recyclerView =
            WeakReference(frgBaseView?.get()?.findViewById(R.id.rvMovies) as RecyclerView)
        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(frgBaseActivity?.get()) //Orientation
        recyclerView?.get()?.layoutManager = manager
        movieList.sortedBy { it.title }

        recyclerView?.get()?.adapter = frgBaseContext?.get()?.let {
            actualMovieList?.let { it2 ->
                MoviesListAdapter(
                    this, it2,
                    it
                )
            }

        }

        fragmentBaseBinding?.moviesHomeVM = this
        recyclerView?.get()?.adapter?.notifyDataSetChanged()
    }


    /**
     *  Buttons & taps functions group
     */

    fun makeFavoriteButton(mMovie: Movie) {

        Toast.makeText(frgBaseContext?.get(), frgBaseContext?.get()?.getString(R.string.movie_saved_db), Toast.LENGTH_LONG)
            .show()

        frgBaseContext?.get()?.let { localRepository.insertMovie(it, mMovie) }
        recyclerView?.get()?.adapter?.notifyDataSetChanged()

    }


    fun deleteFavoriteMovieButton(mMovie: Movie, position: Int) {

        frgBaseContext?.get()?.let { localRepository.deleteMovie(it, mMovie.title) }

        //Refresh recyclerView
        actualMovieList?.removeAt(position)
        savedMovies.removeAt(position)
        recyclerView?.get()?.adapter?.notifyItemRemoved(position)
        recyclerView?.get()?.adapter?.notifyDataSetChanged()

        Toast.makeText(frgBaseContext?.get(), frgBaseContext?.get()?.getString(R.string.delete_movie), Toast.LENGTH_LONG)
            .show()
    }


    fun searchMovieButton() {

        CommonUtil.actualPage = 1
        searchMode = true

        if (fragmentBaseBinding?.etSearch?.text.toString().isBlank()) {
            fragmentBaseBinding?.etSearch?.error = frgBaseContext?.get()?.getString(R.string.error_no_empty)
            Toast.makeText(frgBaseContext?.get(), frgBaseContext?.get()?.getString(R.string.error_no_empty), Toast.LENGTH_LONG)
                .show()
        } else {
            fragmentBaseBinding?.btnSearch?.visibility = View.GONE
            fragmentBaseBinding?.btnRefresh?.visibility = View.VISIBLE

            //Hide keyboard
            frgBaseContext?.get()?.let {
                frgBaseView?.get()?.let { it1 ->
                    CommonUtil.hideKeyboard(
                        it,
                        it1
                    )
                }
            }

            //Show progress bar
            fragmentBaseBinding?.progressBar?.visibility = View.VISIBLE

            //Call to network repository to retrieve data
            frgBaseContext?.get()?.let { it1 ->
                if (CommonUtil.isOnline(it1)) networkRepository.getSearchMovies(
                    fragmentBaseBinding?.etSearch?.text.toString(),
                    it1,
                    retrieveNetworkMovies
                )
                else {
                    Toast.makeText(it1, frgBaseContext?.get()?.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                    fragmentBaseBinding?.progressBar?.visibility = View.GONE
                }
            }
        }
    }

    fun goToFavourites() {
        isTopSelected = false
        frgBaseContext?.get()?.let { localRepository.getAllMovies(it, savedFavouritesMovies) }
        fragmentBaseBinding?.searchLayout?.visibility = View.GONE
        fragmentBaseBinding?.pageIndicatorLayout?.visibility = View.GONE
    }

    fun goToTopMovies() {
        isTopSelected = true
        fragmentBaseBinding?.searchLayout?.visibility = View.VISIBLE
        fragmentBaseBinding?.pageIndicatorLayout?.visibility = View.VISIBLE

        if (lastMoviesList != null) retrieveNetworkMovies.value = lastMoviesList
        else CommonUtil.replaceFragment(
            BaseFragment(),
            (frgBaseContext?.get() as AppCompatActivity).supportFragmentManager
        )

    }

    fun refreshTopMovies() {

        fragmentBaseBinding?.btnSearch?.visibility = View.VISIBLE
        fragmentBaseBinding?.btnRefresh?.visibility = View.GONE

        CommonUtil.actualPage = 1

        CommonUtil.replaceFragment(
            BaseFragment(),
            (frgBaseContext?.get() as AppCompatActivity).supportFragmentManager
        )
    }


    fun goToPage(nextPage: Boolean) {

        when (nextPage) {
            true -> {
                if (CommonUtil.actualPage <= CommonUtil.totalPages - 1) {
                    CommonUtil.actualPage = CommonUtil.actualPage + 1
                    goToPageLogic()
                }
            }
            false -> {
                if (CommonUtil.actualPage > 1) {
                    CommonUtil.actualPage = CommonUtil.actualPage - 1
                    goToPageLogic()
                }
            }
        }
    }

    private fun goToPageLogic() {

        fragmentBaseBinding?.progressBar?.visibility = View.VISIBLE
        fragmentBaseBinding?.pageIndicatorLayout?.visibility = View.GONE
        fragmentBaseBinding?.rvMovies?.visibility = View.GONE

        val runnable = Runnable {
            when (searchMode) {

                true -> {
                    frgBaseContext?.get()?.let { it1 ->
                        fragmentBaseBinding?.pageIndicatorLayout?.visibility = View.VISIBLE

                        if (CommonUtil.isOnline(it1)) networkRepository.getSearchMovies(
                            fragmentBaseBinding?.etSearch?.text.toString(),
                            it1,
                            retrieveNetworkMovies
                        )
                        else {
                            Toast.makeText(it1, frgBaseContext?.get()?.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                            fragmentBaseBinding?.progressBar?.visibility = View.GONE
                        }
                    }
                }
                false -> {
                    CommonUtil.replaceFragment(
                        BaseFragment(),
                        (frgBaseContext?.get() as AppCompatActivity).supportFragmentManager
                    )
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(runnable, 250)
    }


}