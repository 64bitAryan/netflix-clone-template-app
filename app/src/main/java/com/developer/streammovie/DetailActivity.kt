package com.developer.streammovie

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.developer.streammovie.model.recyclerDetails
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val items = intent.getSerializableExtra("Details") as? recyclerDetails
        val transitionName = intent.getStringExtra("transitionName")

        val imageView: ImageView = findViewById(R.id.detail_imageView)
        imageView.transitionName = transitionName

        val poster: ImageView = findViewById(R.id.detail_backdrop)

        val overView = items?.overView
        val imageUrl = items?.imageUrl
        val backDropUrl = items?.posterPath
        val name = items?.name

        detail_textView.text = overView
        detail_titleView.text = name
        Glide.with(this).load(imageUrl).into(imageView)
        Glide.with(this).load(backDropUrl).into(poster)
    }
}