package com.ayan.openinapp.adapters

import Link
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayan.openinapp.R
import com.ayan.openinapp.databinding.ItemLinkBinding
import com.bumptech.glide.Glide

class LinkAdapter(private val data: ArrayList<Link>) :
    RecyclerView.Adapter<LinkAdapter.LinkDetailsViewHolder>() {

    inner class LinkDetailsViewHolder(private val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Link) {
            binding.tvLinkName.text = data.app
            binding.tvLinkDate.text = data.timesAgo
            binding.tvClickNumbers.text = data.totalClicks.toString()
            binding.url.text = data.smartLink

            // Using Glide to load the image into the ImageView
            Glide.with(binding.root.context)
                .load(data.originalImage)
                .centerCrop() //center crop the image
                .error(R.drawable.ic_error)
                .into(binding.imgOrginal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLinkBinding.inflate(inflater, parent, false)
        return LinkDetailsViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LinkDetailsViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }
}