package com.rio.contanctsapplication.view.home_Activity_fragments.favorite_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentFavoriteBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.view.HomeActivity
import com.rio.contanctsapplication.view.home_Activity_fragments.favorite_fragment.adapter.FavoriteContactsAdapter
import com.rio.contanctsapplication.view.home_Activity_fragments.item_selector_fragment.ItemSelectorFragment
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view_model.FFavoritesFragmentViewModelFactory
import com.rio.contanctsapplication.view_model.VFavoritesFragmentViewModel

class FavoriteFragment : Fragment(), FavoriteContactsAdapter.IInteraction, OnClickListener {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoritesFragmentViewModel: VFavoritesFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesFragmentViewModel = ViewModelProvider(this, FFavoritesFragmentViewModelFactory(
            ContactsDatabase.getInstanceOfDataBase(requireActivity())
        ))[VFavoritesFragmentViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this

        // setting up onclick listener for the ui components
        binding.apply {
            favoritesFragmentAddFavoriteButton.setOnClickListener(this@FavoriteFragment)
        }
        requireActivity().intent.getStringExtra("favoriteFragment")?.let {
            makeToastWith(requireActivity(), it)
        }
        initRecyclerView()
        return binding.root
    }

    /**
     * [initRecyclerView] method initializes the recycler view in the Favorites fragment
     */
    private fun initRecyclerView(){
        binding.favoritesFragmentRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = FavoriteContactsAdapter(this@FavoriteFragment)
        favoritesFragmentViewModel.favoriteContacts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.favoritesFragmentRecyclerView.adapter = adapter
    }
    override fun getActivityContext(): Context = requireActivity()

    override fun onItemClickListener(contact: Contact) {
        makeToastWith(requireActivity(), "Success")
    }

    override fun onClick(v: View?) {
        binding.apply {
            when(v){
                favoritesFragmentAddFavoriteButton->{
                    val tempActivity = (requireActivity() as HomeActivity)
                    tempActivity.intent.putExtra("itemSelectorFragment",
                        ItemSelectorFragment.REQUEST_TO_ADD_FAVORITE_CONTACTS
                    )
                    tempActivity.setFragment(ItemSelectorFragment())
                }
            }
        }
    }
}