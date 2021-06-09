package com.developer.streammovie.Adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.streammovie.R
import com.developer.streammovie.model.MovieDetails

class viewPagerAdapter(): RecyclerView.Adapter<viewPagerAdapter.Pager2ViewHolder>() {

    private val items: ArrayList<MovieDetails> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,viewType: Int
    ): viewPagerAdapter.Pager2ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val viewHolder = Pager2ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: viewPagerAdapter.Pager2ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemTitle.text = currentItem.title
        holder.itemOverView.text = currentItem.overView
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.itemImageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.findViewById(R.id.pager_item_Title)
        val itemOverView: TextView = itemView.findViewById(R.id.pager_item_description)
        val itemImageView: ImageView = itemView.findViewById((R.id.paager_item_ImageView))
    }

    fun updateMovie(updatedNew: ArrayList<MovieDetails>) {
        items.clear()
        items.addAll(updatedNew)

        notifyDataSetChanged()
    }
}