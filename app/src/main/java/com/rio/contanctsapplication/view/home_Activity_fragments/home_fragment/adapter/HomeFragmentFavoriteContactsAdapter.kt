package com.rio.contanctsapplication.view.home_Activity_fragments.home_fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Contact
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragmentFavoriteContactsAdapter(private val interaction: IFavoriteContactsInteraction? = null) :
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
                R.layout.favorite_recycler_view_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindDataWithUI(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list:List<Contact>){
        differ.submitList(list)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: IFavoriteContactsInteraction?
    ): RecyclerView.ViewHolder(itemView){
        val contactName:TextView = itemView.findViewById(R.id.favoritesRecyclerViewName)
        val userDp:CircleImageView = itemView.findViewById(R.id.favoritesRecyclerViewDp)
        val removeButton:ImageView = itemView.findViewById(R.id.favoriteRecyclerViewDeleteButton)
        val contactItem:ConstraintLayout = itemView.findViewById(R.id.homeFragmentFavoritesRecyclerViewItem)

        fun bindDataWithUI(contact: Contact){
            interaction?.let { it ->
                contactName.text = contact.contactName
                contactItem.setOnClickListener { _->
                    it.onItemClicked(contact)
                }

                removeButton.setOnClickListener{ _->
                    it.onRemoveClicked(contact)
                }

                it.getHomeActivityContext().let { it1->
                    val drawableResourceId = it1.resources.getIdentifier(
                        "letter_less_background",
                        "drawable",
                        it1.packageName
                    );
//                    Glide.with(it1).load(drawableResourceId).into(userDp)
                };
            }
        }
    }

    interface IFavoriteContactsInteraction{
        fun getHomeActivityContext():Context
        fun onItemClicked(contact:Contact)
        fun onRemoveClicked(contact: Contact)
    }
}