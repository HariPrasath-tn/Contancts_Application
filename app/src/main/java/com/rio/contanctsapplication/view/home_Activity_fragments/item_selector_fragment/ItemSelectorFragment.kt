package com.rio.contanctsapplication.view.home_Activity_fragments.item_selector_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.databinding.FragmentItemSelectorBinding
import com.rio.contanctsapplication.model.database.ContactsDatabase
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Group
import com.rio.contanctsapplication.view.home_Activity_fragments.item_selector_fragment.adapter.ItemSelectorAdapter
import com.rio.contanctsapplication.view_model.FItemSelectorViewModelFactory
import com.rio.contanctsapplication.view_model.VItemSelectorViewModel

class ItemSelectorFragment : Fragment(), ItemSelectorAdapter.IItemInteraction {
    private lateinit var binding:FragmentItemSelectorBinding
    private lateinit var itemSelectorViewModel: VItemSelectorViewModel
    private val fragmentPurpose = listOf<String>("Select contact to add", "Select contacts to add", "Select group to add")
    private val itemsSelected = arrayListOf<Any>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_selector, container, false)
        binding.lifecycleOwner = this
        val type = requireActivity().intent.getIntExtra("itemSelectorFragment", 0)
        type.let {
            binding.textView10.text = fragmentPurpose[type]
            itemSelectorViewModel = ViewModelProvider(
                this, FItemSelectorViewModelFactory(
                    ContactsDatabase.getInstanceOfDataBase(requireActivity()), it
                )
            )[VItemSelectorViewModel::class.java]

            initRecyclerView()
        }


        binding.itemSelectorFragmentAddButton.setOnClickListener{triggerInsertion()}
        return binding.root
    }

    override fun onContactItemClicked(contact: Contact, isChecked:Boolean) {
        if(isChecked){
            if(!itemsSelected.contains(contact)){
                itemsSelected.add(contact)
            }
        }else{
            if(itemsSelected.contains(contact)){
                itemsSelected.remove(contact)
            }
        }
    }

    /**
     * [triggerInsertion] method trigger contact insertion operation into the favorites list in the
     * database
     */
    private fun triggerInsertion() {
        itemSelectorViewModel.insertContactIntoFavorites(itemsSelected as List<Contact>)
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onGroupItemClicked(groupItem: Group) {
    }

    /**
     * [initRecyclerView] method initializes the recyclerView
     */
    private fun initRecyclerView(){
        binding.itemSelectorRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ItemSelectorAdapter(this@ItemSelectorFragment)
        itemSelectorViewModel.itemList .observe(viewLifecycleOwner){
            (it as? List<Contact>)?.let { it1 -> adapter.submitList(it1) }
        }
        binding.itemSelectorRecyclerView.adapter = adapter
    }

    companion object{
        const val REQUEST_TO_ADD_FAVORITE_CONTACTS = 0
        const val REQUEST_TO_ADD_CONTACTS_TO_GROUP = 1
        const val REQUEST_TO_ADD_GROUP = 2
    }
}