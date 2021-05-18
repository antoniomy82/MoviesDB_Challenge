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
        fragmentBaseBinding?.moviesHomeVM=moviesHomeViewModel

        //Set base fragment parameters in this VM
        activity?.let { context?.let { it1 ->
            fragmentBaseBinding?.let { it2 ->
                moviesHomeViewModel?.setBaseFragmentBinding(it,
                    it1,view,savedInstanceState, it2
                )
            }
        } }


        context?.let { moviesHomeViewModel?.networkRepository?.getPopularMovies(it) }

        // MovieDbRepository().getMovieDetails(this,500)

        moviesHomeViewModel?.networkRepository?.retrieveMovies?.observe(viewLifecycleOwner) { retrieveMovies ->

            if (retrieveMovies == null)  Toast.makeText(context,"No data found", Toast.LENGTH_LONG).show()
            else {
                retrieveMovies.results?.let { moviesHomeViewModel?.setMoviesRecyclerViewAdapter(it) }

                fragmentBaseBinding?.tabTop?.setBackgroundResource(R.drawable.bg_rounded_selected)
            }
            //hide progress bar
            fragmentBaseBinding?.progressBar?.visibility = View.GONE
        }






    }
}