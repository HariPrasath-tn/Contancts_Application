package com.rio.contanctsapplication.view.home_Activity_fragments.home_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentHomeFragmentBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.view.HomeActivity
import com.rio.contanctsapplication.view.home_Activity_fragments.home_fragment.adapter.HomeFragmentFavoriteContactsAdapter
import com.rio.contanctsapplication.view.home_Activity_fragments.home_fragment.adapter.HomeFragmentGroupsRecyclerViewAdapter
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_home_fragment.ContactsFragment
import com.rio.contanctsapplication.view.home_Activity_fragments.favorite_fragment.FavoriteFragment
import com.rio.contanctsapplication.view.home_Activity_fragments.group_fragment.GroupsFragment
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view_model.FHomeFragmentViewModelFactory
import com.rio.contanctsapplication.view_model.VHomeFragmentViewModel

class HomeFragment : Fragment(), OnClickListener,
    HomeFragmentFavoriteContactsAdapter.IFavoriteContactsInteraction,
    HomeFragmentGroupsRecyclerViewAdapter.IGroupItemInteraction{
    private lateinit var binding:FragmentHomeFragmentBinding
    private lateinit var homeFragmentViewModel: VHomeFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_fragment, container, false)
        binding.lifecycleOwner = this
        binding.homeFragmentGroupsViewMoreButton.setOnClickListener(this)
        binding.homeFragmentfavoriteViewMoreButton.setOnClickListener(this)
        binding.homeTop.homeFragmentContactCountLayout.setOnClickListener(this)
        binding.homeTop.homeFragmentGroupsCountLayout.setOnClickListener(this)
        binding.homeTop.homeFragmentFavoriteCountLayout.setOnClickListener(this)
        homeFragmentViewModel = ViewModelProvider(this, FHomeFragmentViewModelFactory(
            ContactsDatabase.getInstanceOfDataBase(requireActivity())
        ))[VHomeFragmentViewModel::class.java]

        initFavoritesRecyclerView()
        initGroupsRecyclerView()
        binding.homeTop.homeFragmentViewModel = homeFragmentViewModel
        return binding.root
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                homeFragmentGroupsViewMoreButton -> {
                    (requireActivity() as HomeActivity).setFragment(GroupsFragment())
                }
                homeFragmentfavoriteViewMoreButton -> {
                    (requireActivity() as HomeActivity).setFragment(FavoriteFragment())
                }
                homeTop.homeFragmentContactCountLayout->{
                    val tempActivity = (requireActivity() as HomeActivity)
                    tempActivity.setFragment(ContactsFragment())
                }
                homeTop.homeFragmentGroupsCountLayout->{
                    (requireActivity() as HomeActivity).setFragment(GroupsFragment())
                }
                homeTop.homeFragmentFavoriteCountLayout->{
                    (requireActivity() as HomeActivity).setFragment(FavoriteFragment())
                }
            }
        }
    }


    private fun initFavoritesRecyclerView(){
        val adapter = HomeFragmentFavoriteContactsAdapter(this@HomeFragment)
        homeFragmentViewModel.favoriteContacts.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.FavoritesRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.FavoritesRecyclerView.adapter = adapter
    }

    private fun initGroupsRecyclerView(){
        val adapter = HomeFragmentGroupsRecyclerViewAdapter(this@HomeFragment)
        homeFragmentViewModel.groupsAvail.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.groupsRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.groupsRecyclerView.adapter = adapter
    }

    override fun onItemClicked(contact:Contact) {
        makeToastWith(requireActivity(), "item Clicked ${contact.contactName}")
    }

    override fun onRemoveClicked(contact: Contact) {
        triggerFavoriteRemovalWithConformation(contact)
    }

    override fun getHomeActivityContext(): Context = requireActivity()

    override fun getActivityContext(): Context = requireActivity()

    override fun onGroupItemClicked(group: Group) {
        makeToastWith(requireActivity(), "item Clicked ${group.groupName}")
    }

    override fun onRemoveGroupClicked(group: Group) {
        triggerGroupRemovalWithConformation(group)
    }

    private fun triggerFavoriteRemovalWithConformation(contact:Contact){
        val alertDialog = AlertDialog.Builder(requireActivity()).also {
            it.setTitle("Confirm removing")
            it.setMessage("Do you really want to remove \"${contact.contactName}\" from Favorites")
            it.setPositiveButton("Yes"){_, _ ->
                homeFragmentViewModel.removeFromFavorite(contact)
            }
            it.setNegativeButton("No"){_, _ -> }

            try {
                it.setIcon(R.drawable.app_icon)
            }catch (e:Exception){
                throw IllegalArgumentException("Expecting resource id(R.drawable.[iconName]), " +
                        "but provide irrelevant")
            }
        }
        alertDialog.show()
    }

    private fun triggerGroupRemovalWithConformation(group:Group){
        val alertDialog = AlertDialog.Builder(requireActivity()).also {
            it.setTitle("Confirm removing")
            it.setMessage("Do you really want to delete \"${group.groupName}\" ?")
            it.setPositiveButton("Yes"){_, _ ->
                homeFragmentViewModel.deleteGroup(group)
            }
            it.setNegativeButton("No"){_, _ -> }

            try {
                it.setIcon(R.drawable.app_icon)
            }catch (e:Exception){
                throw IllegalArgumentException("Expecting resource id(R.drawable.[iconName]), " +
                        "but provide irrelevant")
            }
        }
        alertDialog.show()
    }
}