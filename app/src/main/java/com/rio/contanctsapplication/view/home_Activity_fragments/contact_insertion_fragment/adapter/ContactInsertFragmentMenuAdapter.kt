package com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.adapter

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
import com.rio.contanctsapplication.view.home_Activity_fragments.contact_insertion_fragment.MenuItem
import de.hdodenhof.circleimageview.CircleImageView

class ContactInsertFragmentMenuAdapter(private val interaction: IMenuInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MenuItem>(){
        override fun areItemsTheSame(oldItem: MenuItem, newITem: MenuItem):Boolean {
            return oldItem == newITem
        }

        override fun areContentsTheSame(oldItem: MenuItem, newITem: MenuItem):Boolean {
            return oldItem.itemName == newITem.itemName
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                 R.layout.contact_insertion_menu_recyclerview_item,
                parent,
                false
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

    fun submitList(list:List<MenuItem>){
        differ.submitList(list)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: IMenuInteraction?
    ): RecyclerView.ViewHolder(itemView){
        fun bindUi(menuItem: MenuItem){
            interaction?.let {

                menuName.text = menuItem.itemName
                menuCard.setOnClickListener {_->
                    it.onMenuItemClicked(menuItem)
                }

                it.getActivityContext().let { it1->
                    val drawableResourceId = it1.resources.getIdentifier(
                        menuItem.itemIcon,
                        "drawable",
                        it1.packageName
                    );
                    Glide.with(it1).load(drawableResourceId).into(menuIcon)
                };
            }
        }
        private val menuIcon:CircleImageView = itemView.findViewById(R.id.menuIcon)
        private val menuName:TextView = itemView.findViewById(R.id.menuName)
        private val menuCard:ConstraintLayout = itemView.findViewById(R.id.menuIconLayout)
    }

    interface IMenuInteraction{
        fun onMenuItemClicked(menuItem: MenuItem)
        fun getActivityContext():Context
    }
}