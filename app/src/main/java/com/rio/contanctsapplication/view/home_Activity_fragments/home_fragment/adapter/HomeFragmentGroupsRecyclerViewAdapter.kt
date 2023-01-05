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
import com.bumptech.glide.Glide
import com.rio.contanctsapplication.R
import com.rio.contanctsapplication.model.database.entities.Group
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragmentGroupsRecyclerViewAdapter(private val interaction: IGroupItemInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Group>(){
        override fun areItemsTheSame(oldItem:Group, newITem:Group):Boolean {
            return oldItem == newITem
        }

        override fun areContentsTheSame(oldItem:Group, newITem:Group):Boolean {
            return oldItem.groupName == newITem.groupName
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

    fun submitList(list:List<Group>){
        differ.submitList(list)
    }

    class ViewHolder(
        itemView: View,
        private val interaction: IGroupItemInteraction?
    ): RecyclerView.ViewHolder(itemView){
        val groupName: TextView = itemView.findViewById(R.id.favoritesRecyclerViewName)
        val groupDp: CircleImageView = itemView.findViewById(R.id.favoritesRecyclerViewDp)
        private val removeButton: ImageView = itemView.findViewById(R.id.favoriteRecyclerViewDeleteButton)
        private val groupItem: ConstraintLayout = itemView.findViewById(R.id.homeFragmentFavoritesRecyclerViewItem)

        fun bindDataWithUI(group: Group){
            interaction?.let { it ->
                groupName.text = group.groupName
                groupItem.setOnClickListener { _->
                    it.onGroupItemClicked(group)
                }

                removeButton.setOnClickListener{ _->
                    it.onRemoveGroupClicked(group)
                }

                it.getActivityContext().let { it1->
                    val drawableResourceId = it1.resources.getIdentifier(
                        "group",
                        "drawable",
                        it1.packageName
                    );
                    Glide.with(it1).load(drawableResourceId).into(groupDp)
                }
            }
        }
    }

    interface IGroupItemInteraction{
        fun getActivityContext():Context
        fun onGroupItemClicked(group:Group)
        fun onRemoveGroupClicked(group: Group)
    }
}