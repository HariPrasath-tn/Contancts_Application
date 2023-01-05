package com.rio.contanctsapplication.view.home_Activity_fragments.group_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentGroupsBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.view.HomeActivity
import com.rio.contanctsapplication.view.home_Activity_fragments.group_fragment.adapter.GroupRecyclerViewAdapter
import com.rio.contanctsapplication.view.home_Activity_fragments.new_group_fragment.NewGroupFragment
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view_model.FGroupFragmentViewModelFactory
import com.rio.contanctsapplication.view_model.VGroupFragmentViewModel

class GroupsFragment : Fragment(), GroupRecyclerViewAdapter.IGroupItemInteraction, OnClickListener {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var groupsFragmentViewModel: VGroupFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groupsFragmentViewModel = ViewModelProvider(this,
                FGroupFragmentViewModelFactory(
                    ContactsDatabase.getInstanceOfDataBase(requireActivity())
                )
            )[VGroupFragmentViewModel::class.java]

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_groups, container, false)
        binding.lifecycleOwner = this

        binding.groupFragmentAddGroupButton.setOnClickListener(this)

        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView(){
        binding.groupsListAdapterRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = GroupRecyclerViewAdapter(this@GroupsFragment)
        groupsFragmentViewModel.groupsAvail.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.groupsListAdapterRecyclerView.adapter = adapter
    }
    override fun onItemClicked(group: Group) {
        makeToastWith(requireActivity(), "Success")
    }

    override fun getViewModel(): ViewModel = groupsFragmentViewModel

    override fun getActivityContext(): Context = requireActivity()

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                groupFragmentAddGroupButton -> {
                    val tempActivity = (requireActivity() as HomeActivity)
                    tempActivity.setFragment(NewGroupFragment())
                }
            }
        }
    }
}