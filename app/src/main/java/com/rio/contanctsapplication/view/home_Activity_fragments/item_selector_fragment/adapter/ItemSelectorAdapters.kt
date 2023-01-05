package com.rio.contanctsapplication.view.home_Activity_fragments.item_selector_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Contact
import com.rio.contanctsapplication.model.database.entities.Group

class ItemSelectorAdapter(private val interaction: IItemInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>(){
        override fun areItemsTheSame(oldItem:Contact, newITem:Contact):Boolean {
            return oldItem == newITem
        }

        override fun areContentsTheSame(oldItem:Contact, newITem:Contact):Boolean {
            return oldItem.contactName == newITem.contactName
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_selector_recyclerview_item,
                parent,
                false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is FavoriteContactsViewHolder){
            val tempList = differ.currentList as List<Contact>
            holder.bindUiData(tempList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list:List<Contact>){
        differ.submitList(list)
    }

    class FavoriteContactsViewHolder(
        itemView: View,
        private val interaction: IItemInteraction?
    ): RecyclerView.ViewHolder(itemView){
        private val checkBox: TextView = itemView.findViewById(R.id.itemSelectorFragmentRecyclerViewCheckBox)
        fun bindUiData(contact:Contact){
            checkBox.text = contact.contactName
            checkBox.setOnClickListener {
                if(checkBox is CheckBox){
                    interaction?.onContactItemClicked(contact, checkBox.isChecked)
                }
            }

        }
    }

    interface IItemInteraction{
        fun onContactItemClicked(contact: Contact, isChecked:Boolean)
        fun onGroupItemClicked(groupItem:Group)
    }
}