package com.ayan.openinapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayan.openinapp.databinding.ItemInfoBinding
import com.ayan.openinapp.models.Info

class InfoBoxAdapter(private val data: ArrayList<Info>) :
    RecyclerView.Adapter<InfoBoxAdapter.InfoBoxViewHolder>() {

    inner class InfoBoxViewHolder(private val binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Info) {
            binding.apply {
                icon.setImageResource(data.iconId)
                tvTodayCount.text = data.mainInfo
                subHeading.text = data.subData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoBoxViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInfoBinding.inflate(inflater, parent, false)
        return InfoBoxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoBoxViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = data.size

}