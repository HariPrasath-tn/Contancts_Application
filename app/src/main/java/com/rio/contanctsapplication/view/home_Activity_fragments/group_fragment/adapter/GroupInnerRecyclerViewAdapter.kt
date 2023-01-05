package com.rio.contanctsapplication.view.home_Activity_fragments.group_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Contact
import de.hdodenhof.circleimageview.CircleImageView

class GroupInnerRecyclerViewAdapter:
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
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contacts_recyclerview_item_favorite,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindUi(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list:List<Contact>){
        differ.submitList(list)
    }

    class ViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val dp:CircleImageView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerDp)
        val name: TextView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerName)
        val phoneNumber: TextView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerPhoneNumber)
        val letter: TextView = itemView.findViewById(R.id.contactRecyclerViewMiddleContactNameFirstLetter)
        fun bindUi(contact: Contact){
            letter.text = contact.contactName[0].toString().uppercase()
            name.text = contact.contactName
            phoneNumber.text = contact.phoneNumber
        }
    }
}