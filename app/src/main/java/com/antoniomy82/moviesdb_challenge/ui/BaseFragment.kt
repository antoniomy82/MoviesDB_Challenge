package com.antoniomy82.moviesdb_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.databinding.FragmentBaseBinding
import com.antoniomy82.moviesdb_challenge.utils.CommonUtil
import com.antoniomy82.moviesdb_challenge.utils.Constant
import com.antoniomy82.moviesdb_challenge.viewmodel.MoviesHomeViewModel


class BaseFragment : Fragment() {

    var fragmentBaseBinding: FragmentBaseBinding? = null
    var moviesHomeViewModel: MoviesHomeViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentBaseBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)

        retainInstance = true

        return fragmentBaseBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesHomeViewModel = ViewModelProvider(this).get(MoviesHomeViewModel::class.java)
        fragmentBaseBinding?.moviesHomeVM = moviesHomeViewModel

        //Set base fragment parameters in this VM
        activity?.let {
            context?.let { it1 ->
                fragmentBaseBinding?.let { it2 ->
                    moviesHomeViewModel?.setBaseFragmentBinding(
                        it,
                        it1, view, savedInstanceState, it2
                    )
                }
            }
        }

        //Load saved list
        context?.let {
            moviesHomeViewModel?.savedList?.let { it1 ->
                moviesHomeViewModel?.localRepository?.getSavedMovies(
                    it,
                    it1
                )
            }
        }

        moviesHomeViewModel?.savedList?.observe(viewLifecycleOwner) { listSaved ->
            if (listSaved.isNotEmpty()) {
                moviesHomeViewModel?.savedMovies = listSaved as MutableList<String>
            }

        }

        //Call to networkRepository to show Top movies.
        if (moviesHomeViewModel?.isTopSelected == true) context?.let {
            moviesHomeViewModel?.retrieveNetworkMovies?.let { it1 ->

                moviesHomeViewModel?.networkRepository?.getPopularMovies(
                    it,
                    it1, Constant.dynamicUrl + CommonUtil.actualPage
                )
            }
        }

        //Observer to load Top Movies in recycler view
        moviesHomeViewModel?.retrieveNetworkMovies?.observe(viewLifecycleOwner) { retrieveMovies ->

            if (retrieveMovies == null) Toast.makeText(
                context,
                context?.getString(R.string.no_data_found),
                Toast.LENGTH_LONG
            )
                .show()
            else {
                CommonUtil.actualPage = retrieveMovies.page?.toInt() ?: 0
                CommonUtil.totalPages = retrieveMovies.total_pages?.toInt() ?: 0

                val pageIndicator = retrieveMovies.page + " / " + retrieveMovies.total_pages
                fragmentBaseBinding?.pageIndicator?.text = pageIndicator

                if (CommonUtil.actualPage == 0) fragmentBaseBinding?.pageIndicatorLayout?.visibility =
                    View.GONE


                if (moviesHomeViewModel?.isFavourite == false) moviesHomeViewModel?.lastMoviesList =
                    retrieveMovies //Save last top list

                retrieveMovies.results?.let { moviesHomeViewModel?.setMoviesRecyclerViewAdapter(it) }

                //It change button background
                fragmentBaseBinding?.tabTop?.setBackgroundResource(R.drawable.bg_rounded_selected)
                fragmentBaseBinding?.tabFav?.setBackgroundResource(R.drawable.bg_rounded)
            }
            //hide progress bar
            fragmentBaseBinding?.progressBar?.visibility = View.GONE
            fragmentBaseBinding?.rvMovies?.visibility = View.VISIBLE
        }


        //Observer to load Favourites movies in recycler view
        moviesHomeViewModel?.savedFavouritesMovies?.observe(viewLifecycleOwner) { retrieveSavedMovies ->
            if (retrieveSavedMovies == null) Toast.makeText(
                context,
                context?.getString(R.string.no_saved_movies),
                Toast.LENGTH_LONG
            ).show()
            else {
                moviesHomeViewModel?.setMoviesRecyclerViewAdapter(retrieveSavedMovies)

                //It change button background
                fragmentBaseBinding?.tabTop?.setBackgroundResource(R.drawable.bg_rounded)
                fragmentBaseBinding?.tabFav?.setBackgroundResource(R.drawable.bg_rounded_selected)
            }
        }

    }
}