package com.ayan.openinapp.fragments

import Link
import RecentLink
import TopLink
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayan.openinapp.adapters.LinkAdapter
import com.ayan.openinapp.databinding.FragmentLinkListFragmnetBinding

private const val ARG_PARAM1 = "isFirstTab"

class LinkListFragmnet : Fragment() {
    private var isFirstTab: Boolean? = null
    private lateinit var binding: FragmentLinkListFragmnetBinding
    private var mLinks = arrayListOf<Link>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFirstTab = it.getBoolean(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkListFragmnetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcv.layoutManager = LinearLayoutManager(context)
        binding.rcv.adapter = LinkAdapter(mLinks)
    }

    companion object {
        @JvmStatic
        fun newInstance(isFirstTab: Boolean, links: ArrayList<Link>) = LinkListFragmnet().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_PARAM1, isFirstTab)
            }
            mLinks = links
        }
    }
}