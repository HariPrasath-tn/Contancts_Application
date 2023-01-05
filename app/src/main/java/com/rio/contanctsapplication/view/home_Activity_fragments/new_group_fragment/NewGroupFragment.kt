package com.rio.contanctsapplication.view.home_Activity_fragments.new_group_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.NewGroupCreateLayoutBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.view_model.FGroupFragmentViewModelFactory
import com.rio.contanctsapplication.view_model.VGroupFragmentViewModel

class NewGroupFragment:Fragment(), OnClickListener {
    private lateinit var binding:NewGroupCreateLayoutBinding
    private lateinit var groupFragmentViewModel: VGroupFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_group_create_layout, container, false)
        binding.lifecycleOwner = this
        groupFragmentViewModel = ViewModelProvider(this, FGroupFragmentViewModelFactory(
            ContactsDatabase.getInstanceOfDataBase(requireActivity())
        ))[VGroupFragmentViewModel::class.java]

        // onClick implementation is given by the onClick method
        binding.addNewGroupButton.setOnClickListener(this)
        binding.newGroupViewModel = groupFragmentViewModel

        return binding.root
    }

    override fun onClick(v: View) {
        binding.apply {
            when(v){
                addNewGroupButton-> {
                    groupFragmentViewModel.triggerNewGroupCreation(requireActivity())
                }
            }
        }
    }
}