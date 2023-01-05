package com.rio.contanctsapplication.view.home_Activity_fragments.contact_home_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentContactsBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_home_fragment.adapter.ContactsAdapter
import com.rio.contanctsapplication.view.makeToastWith
import com.rio.contanctsapplication.view_model.VContactFragmentViewModel
import com.rio.contanctsapplication.view_model.FContactFragmentViewModelFactory

class ContactsFragment : Fragment(), ContactsAdapter.ContactsInteraction, SearchView.OnQueryTextListener {
    private lateinit var binding:FragmentContactsBinding
    private lateinit var contactsFragmentViewModel: VContactFragmentViewModel
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)
        binding.lifecycleOwner = this
        contactsFragmentViewModel = ViewModelProvider(this,
            FContactFragmentViewModelFactory(
                ContactsDatabase.getInstanceOfDataBase(requireActivity())
            )
        )[VContactFragmentViewModel::class.java]
        // initializing the recyclerview
        initRecyclerView()
        // contact search listener
        searchView = requireActivity().findViewById<SearchView>(R.id.contactSearchView)
        searchView.setOnQueryTextListener(this)
        return binding.root
    }

    private fun initRecyclerView(){
        binding.contactsListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ContactsAdapter(this@ContactsFragment)
        contactsFragmentViewModel.contacts.observe(viewLifecycleOwner){
            adapter.submitList(it.sortedBy {it2 ->  it2.contactName.uppercase() })
            searchView.setQuery("", false)
        }
        contactsFragmentViewModel.filteredContacts.observe(viewLifecycleOwner){
            if(it != null && it.isNotEmpty()){
                adapter.submitList(it.sortedBy {it2 ->  it2.contactName.uppercase() })
            }
        }
        binding.contactsListRecyclerView.adapter = adapter
    }

    override fun getContext(): Context = requireActivity()

    override fun onContactClickListener(contact: Contact) {
        Toast.makeText(requireActivity(), contact.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        contactsFragmentViewModel.fetchContactWith(query?:"")
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        contactsFragmentViewModel.fetchContactWith(newText?:"")
        return false
    }

    override fun onContactLongClick(view:View, contact: Contact) {
        val popupMenu = PopupMenu(requireActivity(), view)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.contactShareMenuButton -> {
                    makeToastWith(requireActivity(), "share button clicked ${contact.contactName}")
                }
                R.id.contactEditMenuButton -> {
                    makeToastWith(requireActivity(), "Edit button clicked ${contact.contactName}")

                }
                R.id.contactDeleteMenuButton -> {
                    makeToastWith(requireActivity(), "Delete button clicked ${contact.contactName}")

                }
            }
            false
        }
        popupMenu.inflate(R.menu.contact_long_press_menu)
        popupMenu.show()
    }
}