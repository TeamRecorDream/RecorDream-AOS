package com.recodream_aos.recordream.presentation.document

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DocumentViewPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> DreamRecordFragment()
        1 -> NoteFragment()
        else -> throw Exception()
    }
}
