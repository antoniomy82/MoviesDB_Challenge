package com.antoniomy82.moviesdb_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.databinding.FragmentBaseBinding
import com.antoniomy82.moviesdb_challenge.model.MovieDbRepository
import com.antoniomy82.moviesdb_challenge.viewmodel.MoviesHomeViewModel


class BaseFragment : Fragment() {

    var fragmentBaseBinding: FragmentBaseBinding? = null
    var moviesHomeViewModel: MoviesHomeViewModel? = null
    var mRepository= MovieDbRepository()

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


        context?.let { mRepository.getPopularMovies(it) }

        // MovieDbRepository().getMovieDetails(this,500)

       mRepository.retrieveMovies.observe(viewLifecycleOwner) { retrieveMovies ->
            //if (retrieveMovies != null){
            //    Log.d("Observer", retrieveMovies.toString())
                retrieveMovies.results?.let { moviesHomeViewModel?.setMoviesRecyclerViewAdapter(it) }
          //  }
            //else Log.d("No Movie", " no retrieve")
        }

    }
}