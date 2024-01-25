package com.ayan.openinapp.adapters

import Link
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ayan.openinapp.fragments.LinkListFragmnet

class LinkPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val topLinks: List<Link>?,
    private val recentLinks: List<Link>?
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            LinkListFragmnet.newInstance(true, topLinks as ArrayList)
        else
            LinkListFragmnet.newInstance(false, recentLinks as ArrayList)
    }
}