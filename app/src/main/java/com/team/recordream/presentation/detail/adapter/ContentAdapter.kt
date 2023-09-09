package com.team.recordream.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.team.recordream.presentation.detail.DetailBottomSheetFragment

class ContentAdapter(detailFragment: DetailBottomSheetFragment) :
    FragmentStateAdapter(detailFragment) {
    val fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
