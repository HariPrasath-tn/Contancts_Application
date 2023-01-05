package com.rio.contanctsapplication.view.home_Activity_fragments.contact_home_fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Contact
import de.hdodenhof.circleimageview.CircleImageView


class ContactsAdapter(private val interaction: ContactsInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val TOP_ITEM_VIEW = 0
    private val MIDDLE_ITEM_VIEW = 1
    private val BOTTOM_ITEM_VIEW = 2
    private val SINGLE_ITEM_VIEW = 3

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>(){
        override fun areItemsTheSame(oldItem:Contact, newITem:Contact):Boolean {
            return oldItem == newITem
        }

        override fun areContentsTheSame(oldItem:Contact, newITem:Contact):Boolean {
            return oldItem.contactName == newITem.contactName
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemViewType(position: Int): Int {
        val list = differ.currentList
        val initial = list[position].contactName.uppercase()[0]

        val temp = if(position > 0 && list[position-1].contactName.uppercase().startsWith(initial) && position < list.size-1 && list[position+1].contactName.uppercase().startsWith(initial)){
            MIDDLE_ITEM_VIEW
        }else if(position > 0 && list[position-1].contactName.uppercase().startsWith(initial) && position < list.size-1 && !list[position+1].contactName.uppercase().startsWith(initial)){
            BOTTOM_ITEM_VIEW
        }else if(position < list.size-1 && list[position+1].contactName.uppercase().startsWith(initial)){
            TOP_ITEM_VIEW
        }else {
            SINGLE_ITEM_VIEW
        }
        return temp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TOP_ITEM_VIEW-> TopViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.contacts_recyclerview_item_top,
                    parent, false), interaction)
            MIDDLE_ITEM_VIEW-> MiddleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.contacts_recyclerview_item_middle,
                    parent, false), interaction)
            BOTTOM_ITEM_VIEW-> BottomViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.contacts_recyclerview_item_bottom,
                    parent, false), interaction)
            else-> SingleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.contacts_recyclerview_single_item,
                    parent, false), interaction)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TopViewHolder -> {
                holder.bindAll(differ.currentList[position])
            }
            is MiddleViewHolder -> {
                holder.bindAll(differ.currentList[position])
            }
            is BottomViewHolder -> {
                holder.bindAll(differ.currentList[position])
            }
            is SingleViewHolder -> {
                holder.bindAll(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list:List<Contact>){
        differ.submitList(list)
    }

    class TopViewHolder(
        itemView: View,
        private val interaction: ContactsInteraction?
    ): RecyclerView.ViewHolder(itemView){
        private val layout:ConstraintLayout = itemView.findViewById(R.id.topItemLayout)
        private val dp:CircleImageView = itemView.findViewById(R.id.contactsRecyclerViewTopItemUSerDp)
        private val name:TextView = itemView.findViewById(R.id.contactsRecyclerViewTopItemUSerName)
        private val phoneNumber:TextView = itemView.findViewById(R.id.contactsRecyclerViewTopItemUSerPhoneNumber)
        private val letter:TextView = itemView.findViewById(R.id.contactsRecyclerViewTopItemLetter)
        private val dpLetter:TextView = itemView.findViewById(R.id.contactRecyclerViewTopContactNameFirstLetter)
        fun bindAll(contact: Contact){
            interaction?.let {
                val drawableResourceId = it.getContext().resources.getIdentifier("letter_less_background", "drawable", it.getContext().packageName);
                dpLetter.visibility = View.VISIBLE
                dpLetter.text = contact.contactName[0].toString().uppercase()
                Glide.with(it.getContext()).load(drawableResourceId).into(dp);
            }
            letter.text = "${contact.contactName[0]}".uppercase()
            name.text = contact.contactName
            phoneNumber.text = contact.phoneNumber
            layout.setOnClickListener {
                interaction?.onContactClickListener(contact)
            }
            layout.setOnLongClickListener {
                interaction?.onContactLongClick(it, contact)
                return@setOnLongClickListener true
            }
        }
    }

    class MiddleViewHolder(
        itemView: View,
        private val interaction: ContactsInteraction?
    ): RecyclerView.ViewHolder(itemView){
        private val layout:ConstraintLayout = itemView.findViewById(R.id.middleItemLayout)
        private val dp:CircleImageView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerDp)
        private val name:TextView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerName)
        private val phoneNumber:TextView = itemView.findViewById(R.id.contactsRecyclerViewMiddleItemUSerPhoneNumber)
        private val dpLetter:TextView = itemView.findViewById(R.id.contactRecyclerViewMiddleContactNameFirstLetter)

        fun bindAll(contact: Contact){
            interaction?.let {
                val drawableResourceId = it.getContext().resources.getIdentifier("letter_less_background", "drawable", it.getContext().packageName);
                dpLetter.visibility = View.VISIBLE
                dpLetter.text = contact.contactName[0].toString().uppercase()
                Glide.with(it.getContext()).load(drawableResourceId).into(dp);
            }
            name.text = contact.contactName
            phoneNumber.text = contact.phoneNumber
            layout.setOnClickListener {
                interaction?.onContactClickListener(contact)
            }
            layout.setOnLongClickListener {
                interaction?.onContactLongClick(it, contact)
                return@setOnLongClickListener true
            }
        }
    }

    class BottomViewHolder(
        itemView: View,
        private val interaction: ContactsInteraction?
    ): RecyclerView.ViewHolder(itemView){
        private val layout:ConstraintLayout = itemView.findViewById(R.id.bottomItemLayout)
        private val dp:CircleImageView = itemView.findViewById(R.id.contactsRecyclerViewBottomItemUSerDp)
        private val name:TextView = itemView.findViewById(R.id.contactsRecyclerViewBottomItemUSerName)
        private val phoneNumber:TextView = itemView.findViewById(R.id.contactsRecyclerViewBottomItemUSerPhoneNumber)
        private val dpLetter:TextView = itemView.findViewById(R.id.contactRecyclerViewBottomContactNameFirstLetter)

        fun bindAll(contact: Contact){
            interaction?.let {
                val drawableResourceId = it.getContext().resources.getIdentifier("letter_less_background", "drawable", it.getContext().packageName);
                dpLetter.visibility = View.VISIBLE
                dpLetter.text = contact.contactName[0].toString().uppercase()
                Glide.with(it.getContext()).load(drawableResourceId).into(dp);
            }
            name.text = contact.contactName
            phoneNumber.text = contact.phoneNumber
            layout.setOnClickListener {
                interaction?.onContactClickListener(contact)
            }
            layout.setOnLongClickListener {
                interaction?.onContactLongClick(it, contact)
                return@setOnLongClickListener true
            }
        }
    }

    class SingleViewHolder(
        itemView: View,
        private val interaction: ContactsInteraction?
    ): RecyclerView.ViewHolder(itemView){
        val layout:ConstraintLayout = itemView.findViewById(R.id.singleItemLayout)
        private val dp:CircleImageView = itemView.findViewById(R.id.contactsRecyclerViewSingleItemUSerDp)
        val name:TextView = itemView.findViewById(R.id.contactsRecyclerViewSingleItemUSerName)
        private val phoneNumber:TextView = itemView.findViewById(R.id.contactsRecyclerViewSingleItemUSerPhoneNumber)
        private val letter:TextView = itemView.findViewById(R.id.contactsRecyclerViewSingleItemLetter)
        private val dpLetter:TextView = itemView.findViewById(R.id.contactRecyclerViewSingleContactNameFirstLetter)

        fun bindAll(contact: Contact){
            interaction?.let {
                val drawableResourceId = it.getContext().resources.getIdentifier("letter_less_background", "drawable", it.getContext().packageName);
                dpLetter.visibility = View.VISIBLE
                dpLetter.text = contact.contactName[0].toString().uppercase()
                Glide.with(it.getContext()).load(drawableResourceId).into(dp);
            }

            letter.text = "${contact.contactName[0]}".uppercase()
            name.text = contact.contactName
            phoneNumber.text = contact.phoneNumber
            layout.setOnClickListener {
                interaction?.onContactClickListener(contact)
            }
            layout.setOnLongClickListener {
                interaction?.onContactLongClick(it, contact)
                return@setOnLongClickListener true
            }
        }
    }

    interface ContactsInteraction{
        fun onContactClickListener(contact: Contact)
        fun getContext(): Context
        fun onContactLongClick(view:View, contact: Contact)
    }
}