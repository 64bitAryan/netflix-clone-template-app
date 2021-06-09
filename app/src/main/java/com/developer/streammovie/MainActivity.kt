package com.developer.streammovie

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.developer.streammovie.Adapters.originalsAdapter
import com.developer.streammovie.Adapters.viewPagerAdapter
import com.developer.streammovie.model.MovieDetails
import com.developer.streammovie.model.MySingleton
import com.developer.streammovie.model.recyclerDetails
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.recycler_item.*
import me.relex.circleindicator.CircleIndicator3

class MainActivity : AppCompatActivity(), originalsAdapter.MovieItemClicked {
    object API_KEY{
        val KEY = "94fde5efbdc13b4912bdc383d97ab3ff"
    }
    //for Loading Poster
    private val baseUrl = "https://image.tmdb.org/t/p/original/"

    private lateinit var mAdapter: viewPagerAdapter
    private lateinit var oAdapter: originalsAdapter
    private lateinit var tAdapter: originalsAdapter
    private lateinit var trAdapter: originalsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        loadOriginals()
        loadTopRated()
        loadAction()

        mAdapter = viewPagerAdapter()
        oAdapter = originalsAdapter(this)
        tAdapter = originalsAdapter(this)
        trAdapter = originalsAdapter(this)

        setviewPager()
        setOriginals()
        setTrending()
        setTopRated()
    }
    private fun setviewPager(){
        view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(view_pager)
        view_pager.adapter = mAdapter
    }

    @SuppressLint("WrongConstant")
    private fun setOriginals() {
        oAdapter.apply {
            original_recyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayout.HORIZONTAL,
                false
            )
        }
        original_recyclerView.adapter = oAdapter
    }

    @SuppressLint("WrongConstant")
    private fun setTrending() {
        tAdapter.apply {
            trending_recyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayout.HORIZONTAL,
                false
            )
        }
        trending_recyclerView.adapter = tAdapter
    }

    @SuppressLint("WrongConstant")
    private fun setTopRated() {
        trAdapter.apply {
            toprated_recyclerVire.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayout.HORIZONTAL,
                false
            )
        }
        toprated_recyclerVire.adapter = trAdapter
    }

    private fun loadData() {
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=${API_KEY.KEY}&with_networks=213"
        val movieArray = ArrayList<MovieDetails>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //Log.d("Main Activity", response.toString())
                val movieJsonArray = response.getJSONArray("results")

                for (i in 0..5) {
                    val movieJsonObject = movieJsonArray.getJSONObject(i)
                    val movie = MovieDetails(
                        movieJsonObject.getString("name"),
                        baseUrl + movieJsonObject.getString("backdrop_path"),
                        movieJsonObject.getString("overview")
                    )
                    movieArray.add(movie)
                }

                // Debugging
                Log.d("Movie Array result: ",movieArray.toString())
                mAdapter.updateMovie(movieArray)
            },
            { error ->
                Log.d("Main Activity", error.toString())
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun loadOriginals(){

        val url = "https://api.themoviedb.org/3/discover/tv?api_key=${API_KEY.KEY}&with_networks=213"
        val movieArray = ArrayList<recyclerDetails>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //Log.d("Main Activity", response.toString())
                val movieJsonArray = response.getJSONArray("results")

                for (i in 0 until movieJsonArray.length()) {
                    val movieJsonObject = movieJsonArray.getJSONObject(i)
                    val movie = recyclerDetails(
                        movieJsonObject.getString("name"),
                        baseUrl + movieJsonObject.getString("poster_path"),
                        movieJsonObject.getString("overview"),
                        baseUrl + movieJsonObject.getString("backdrop_path")
                    )
                    movieArray.add(movie)
                }

                // Debugging
                Log.d("Movie Array result: ",movieArray.toString())
                oAdapter.updateMovie(movieArray)
            },
            { error ->
                Log.d("Main Activity", error.toString())
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun loadAction() {
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY.KEY}&with_genres=28"
        val movieArray = ArrayList<recyclerDetails>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //Log.d("Main Activity", response.toString())
                val movieJsonArray = response.getJSONArray("results")

                for (i in 0 until movieJsonArray.length()) {
                    val movieJsonObject = movieJsonArray.getJSONObject(i)
                    val movie = recyclerDetails(
                        movieJsonObject.getString("original_title"),
                        baseUrl + movieJsonObject.getString("poster_path"),
                        movieJsonObject.getString("overview"),
                        baseUrl + movieJsonObject.getString("backdrop_path")
                    )
                    movieArray.add(movie)
                }

                // Debugging
                Log.d("Movie Array result: ",movieArray.toString())
                tAdapter.updateMovie(movieArray)
            },
            { error ->
                Log.d("Main Activity", error.toString())
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun loadTopRated() {
        val url = "https://api.themoviedb.org/3/movie/top_rated?api_key=${API_KEY.KEY}&language=en-US"
        val movieArray = ArrayList<recyclerDetails>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //Log.d("Main Activity", response.toString())
                val movieJsonArray = response.getJSONArray("results")

                for (i in 0 until movieJsonArray.length()) {
                    val movieJsonObject = movieJsonArray.getJSONObject(i)
                    val movie = recyclerDetails(
                        movieJsonObject.getString("title"),
                        baseUrl + movieJsonObject.getString("poster_path"),
                        movieJsonObject.getString("overview"),
                        baseUrl + movieJsonObject.getString("backdrop_path")
                    )
                    movieArray.add(movie)
                }

                // Debugging
                Log.d("Movie Array result: ",movieArray.toString())
                trAdapter.updateMovie(movieArray)
            },
            { error ->
                Log.d("Main Activity", error.toString())
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(holder:Int, item: recyclerDetails, view: ImageView) {

        val intent = Intent(this, DetailActivity::class.java)
        val option = ActivityOptions
            .makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view))
        intent.putExtra("Details", item)
        intent.putExtra("transitionName", ViewCompat.getTransitionName(view))
        startActivity(intent, option.toBundle())
    }
}