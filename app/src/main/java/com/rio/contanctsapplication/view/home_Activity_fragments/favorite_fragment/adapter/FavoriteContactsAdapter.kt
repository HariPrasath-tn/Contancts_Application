package com.rio.contanctsapplication.view.home_Activity_fragments.favorite_fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Contact
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteContactsAdapter(private val interaction: IInteraction? = null) :
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
                parent, false
            ),
            interaction
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
        itemView: View,
        private val interaction: IInteraction?
    ): RecyclerView.ViewHolder(itemView){
        val dp:CircleImageView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerDp)
        val userName: TextView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerName)
        val phoneNumber: TextView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerPhoneNumber)
        val letter :TextView = itemView.findViewById(R.id.contactRecyclerViewMiddleContactNameFirstLetter)
        val item: ConstraintLayout = itemView.findViewById(R.id.middleItemLayout)
        fun bindUi(contact: Contact){
            userName.text = contact.contactName
            phoneNumber.text = contact.phoneNumber
            letter.text = contact.contactName[0].toString().uppercase()
            item.setOnClickListener {
                interaction?.onItemClickListener(contact)
            }
        }
    }

    interface IInteraction{
        fun getActivityContext(): Context
        fun onItemClickListener(contact:Contact)
    }
}