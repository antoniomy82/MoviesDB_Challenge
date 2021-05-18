package com.antoniomy82.moviesdb_challenge.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.databinding.AdapterMoviesListBinding
import com.antoniomy82.moviesdb_challenge.model.Movie
import com.antoniomy82.moviesdb_challenge.utils.Constant
import com.antoniomy82.moviesdb_challenge.viewmodel.MoviesHomeViewModel
import com.squareup.picasso.Picasso


class MoviesListAdapter(
    private val moviesVM: MoviesHomeViewModel,
    private val moviesList: List<Movie>,
    private val context: Context
) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {


    //Inflate view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_movies_list,
            parent,
            false
        )
    )


    //Binding each element with object element
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.adapterMoviesListBinding.moviesVM = moviesVM
        holder.adapterMoviesListBinding.mMovie = moviesList[position]

        //Set image
        if (moviesList[position].backdrop_path?.isNotEmpty() == true) {
            Picasso.get().load(Constant.basePoster+moviesList[position].backdrop_path)
                .placeholder(R.mipmap.no_image)
                .resize(426, 320)
                .into(holder.adapterMoviesListBinding.imageMovie)
        }


        //Set background color so that different cells are noticeable
        if (position % 2 == 0) holder.adapterMoviesListBinding.root.setBackgroundColor(
            Color.parseColor(
                "#96b5ce"
            )
        )
        else holder.adapterMoviesListBinding.root.setBackgroundColor(Color.parseColor("#dce6ee"))


        //on Click item - open URL
        holder.adapterMoviesListBinding.root.setOnClickListener {

            /*
            RecipesUtils.replaceFragment(moviesList[position].href?.let { it1 ->
                ShowHrefFragment(
                    it1,
                    moviesVM
                )
            }, (context as AppCompatActivity).supportFragmentManager) */
        }

        //on Click  make favorite button
        holder.adapterMoviesListBinding.btnMakeFavorite.setOnClickListener {

            holder.adapterMoviesListBinding.btnMakeFavorite.visibility = View.GONE
            moviesVM.makeFavoriteButton(moviesList[position])
        }

        if (moviesVM.isFavourite) holder.adapterMoviesListBinding.btnMakeFavorite.visibility =
            View.GONE
        else holder.adapterMoviesListBinding.btnMakeFavorite.visibility = View.VISIBLE

    }


    override fun getItemCount(): Int {
        return moviesList.size
    }

    class ViewHolder(val adapterMoviesListBinding: AdapterMoviesListBinding) :
        RecyclerView.ViewHolder(adapterMoviesListBinding.root)

}
