package com.developer.streammovie.Adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.streammovie.R
import com.developer.streammovie.model.recyclerDetails

class originalsAdapter(private val listener: MovieItemClicked): RecyclerView.Adapter<originalsAdapter.originalsViewHolder>() {

    private val items: ArrayList<recyclerDetails> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): originalsAdapter.originalsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent,
            false
        )
        val viewHolder = originalsViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: originalsAdapter.originalsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemTitle.text = currentItem.name
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.itemImageView)
        ViewCompat.setTransitionName(holder.itemImageView, currentItem.name)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(holder.adapterPosition, currentItem, holder.itemImageView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class originalsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.findViewById(R.id.original_textView)
        val itemImageView: ImageView = itemView.findViewById(R.id.original_imageView)
    }

    fun updateMovie(updatedNew: ArrayList<recyclerDetails>) {
        items.clear()
        items.addAll(updatedNew)

        notifyDataSetChanged()
    }

    interface MovieItemClicked {
        fun onItemClicked(holder:Int, item: recyclerDetails, view: ImageView)
    }
}