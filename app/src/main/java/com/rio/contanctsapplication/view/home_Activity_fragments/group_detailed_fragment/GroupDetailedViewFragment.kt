package com.rio.contanctsapplication.view.home_Activity_fragments.group_detailed_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rio.contanctsapplication.R

class GroupDetailedViewFragments : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_detailed_viewfragments, container, false)
    }
}