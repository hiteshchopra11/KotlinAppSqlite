package com.example.loginapp.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.loginapp.Fragments.Contact_Us
import com.example.loginapp.Fragments.Images
import com.example.loginapp.Fragments.View_Images


class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return Contact_Us() //ChildFragment1 at position 0
            1 -> return Images() //ChildFragment2 at position 1
            2 -> return View_Images() //ChildFragment3 at position 2
        }
        return Contact_Us() //does not happen
    }

    override fun getCount(): Int {
        return 3 //three fragments
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title = getItem(position).javaClass.name
        title = title.replace("_".toRegex(), " ")
        return title.subSequence(title.lastIndexOf(".") + 1, title.length)
    }
}