package com.example.mrt.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<Int>()

    fun addFragment(fragment: Fragment, title: Int) {
        fragments.add(fragment)
        titles.add(title)
    }

    fun getTitle(position: Int): Int = titles[position]

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}
